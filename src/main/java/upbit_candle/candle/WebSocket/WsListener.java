package upbit_candle.candle.WebSocket;


import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;
import upbit_candle.candle.Entity.ConclusionEntity;
import upbit_candle.candle.Entity.Result.Conclusion;
import upbit_candle.candle.Entity.Result.OrderBookResult;
import upbit_candle.candle.Entity.Result.TickResult;
import upbit_candle.candle.Entity.Result.TradeResult;
import upbit_candle.candle.Service.ConclusionService;

/*
    https://sas-study.tistory.com/432
 */

@Component
public final class WsListener extends WebSocketListener {
    private static final int NORMAL_CLOSURE_STATUS = 1000;
    private Gson gson = new Gson();
    private String json;
    private Conclusion conclusion;
    private ConclusionEntity cResult;
    private BigDecimal p;

    private final ConclusionService service;
    @Autowired
    public WsListener(ConclusionService service){
        this.service = service;
    }

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

    @Override
    public void onFailure(@NotNull WebSocket webSocket, @NotNull Throwable t, @Nullable Response response) {
        System.out.println("Socket Error : " + t.getMessage());
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
//                cResult = gson.fromJson(bytes.string(StandardCharsets.UTF_8), ConclusionEntity.class);
                if(cResult.getReal_price().compareTo(p) < 0) break;
                service.save(cResult);

                System.out.println(tradeResult);
                break;
            case ticker:
                TickResult result = gson.fromJson(bytes.string(StandardCharsets.UTF_8), TickResult.class);
                System.out.println(result);
                break;
            case orderbook:
                System.out.println(gson.fromJson(bytes.string(StandardCharsets.UTF_8), OrderBookResult.class));
                break;
            default:
                throw new RuntimeException("지원하지 않는 웹소켓 조회 유형입니다. : " + conclusion.getType());
        }

    }

    @Override
    public void onOpen(@NotNull WebSocket webSocket, @NotNull Response response) {
        webSocket.send(getParameter());
//        webSocket.close(NORMAL_CLOSURE_STATUS, null); // 없을 경우 끊임없이 서버와 통신함
    }

    public void setParameter(Conclusion conclusion, List<String> codes, Long pivot) {
        this.conclusion = conclusion;
        this.json = gson.toJson(List.of(Ticket.of(UUID.randomUUID().toString()), Type.of(conclusion, codes)));
        this.p = new BigDecimal(pivot);
    }

    private String getParameter() {
        System.out.println(json);
        return this.json;
    }

    public ConclusionEntity getcResult(){
        return this.cResult;
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
