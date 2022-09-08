package upbit_candle.candle.WebSocket;


import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.gson.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import okio.ByteString;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import upbit_candle.candle.Entity.ConclusionEntity;
import upbit_candle.candle.Entity.Result.Conclusion;
import upbit_candle.candle.Entity.Result.TradeResult;

/*
    ref.
    https://sas-study.tistory.com/432
 */

@RequiredArgsConstructor
public final class WsListener extends WebSocketListener {
    private static final int NORMAL_CLOSURE_STATUS = 1000;
    private final Gson gson = new Gson();
    private String json;
    private Conclusion conclusion;
    private ConclusionEntity cResult;
    private final RabbitTemplate rabbitTemplate;
    private static final String topicExchangeName = "market.exchange";

    @Override
    public void onClosed(@NotNull WebSocket webSocket, int code, @NotNull String reason) {
        System.out.printf("Socket Closed : %s / %s\r\n", code, reason);
    }

    @Override
    public void onClosing(@NotNull WebSocket webSocket, int code, @NotNull String reason) {
        System.out.printf("Socket Closing : %s / %s\n", code, reason);
        webSocket.close(NORMAL_CLOSURE_STATUS, null);
        webSocket.cancel();
    }

    @SneakyThrows
    @Override
    public void onFailure(@NotNull WebSocket webSocket, @NotNull Throwable t, @Nullable Response response) {
        System.out.println("Socket Error : " + t.getMessage() + " socket Reconnecting...");

        webSocket.cancel();
        webSocket.send(getParameter());
    }


    @Override
    public void onMessage(@NotNull WebSocket webSocket, @NotNull String text) {
        JsonNode jsonNode = gson.fromJson(text, JsonNode.class);
        System.out.println(jsonNode.toPrettyString());
    }

    @SneakyThrows
    @Override
    public void onMessage(@NotNull WebSocket webSocket, @NotNull ByteString bytes) {
        switch(conclusion) {
            case trade:
                TradeResult tradeResult = gson.fromJson(bytes.string(StandardCharsets.UTF_8), TradeResult.class);
                cResult = new ConclusionEntity(tradeResult.getCode(), tradeResult.getTrade_timestamp(), tradeResult.getTrade_price(), tradeResult.getTrade_volume(), tradeResult.getAsk_bid(), tradeResult.getTrade_date(), tradeResult.getTrade_time());
                rabbitTemplate.convertAndSend(topicExchangeName, "market.*", cResult);
                break;
            default:
                throw new RuntimeException("지원하지 않는 웹소켓 조회 유형입니다. : " + conclusion.getType());
        }
    }

    @Override
    public void onOpen(@NotNull WebSocket webSocket, @NotNull Response response) {
        webSocket.send(getParameter());
    }

    public void setParameter(String UUID, Conclusion conclusion, List<String> codes, Long pivot) {
        this.conclusion = conclusion;
        this.json = gson.toJson(List.of(Ticket.of(UUID), Type.of(conclusion, codes)));
    }

    private String getParameter() {
        System.out.println(json);
        return this.json;
    }

    @Data(staticConstructor = "of")
    private static class Ticket {
        private final String ticket;
    }

    @Data(staticConstructor = "of")
    private static class Type {
        private final Conclusion type;
        private final List<String> codes; // market
        private Boolean isOnlySnapshot = false;
        private Boolean isOnlyRealtime = true;
    }
}
