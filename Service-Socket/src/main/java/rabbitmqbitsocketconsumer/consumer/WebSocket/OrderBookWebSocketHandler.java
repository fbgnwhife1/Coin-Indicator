package rabbitmqbitsocketconsumer.consumer.WebSocket;

import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import rabbitmqbitsocketconsumer.consumer.DTO.BSIList;
import rabbitmqbitsocketconsumer.consumer.DTO.BSI_RBIDto;
import rabbitmqbitsocketconsumer.consumer.Entity.ConclusionEntity;
import rabbitmqbitsocketconsumer.consumer.WebSocket.WebSocketDto.WebSocketDto;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

@Component
@RequiredArgsConstructor
public class OrderBookWebSocketHandler extends TextWebSocketHandler {

    private final ConcurrentHashMap<String, ArrayList<String>> CLIENTS = new ConcurrentHashMap<String,  ArrayList<String>>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {

    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        ArrayList<String> marketList = CLIENTS.get(session.getId());
        if(marketList!=null && !marketList.isEmpty()) {
            for (String market : marketList) {
                OrderBookSessionMap.map.get(market).remove(session);
            }
        }

        CLIENTS.remove(session.getId());
        OrderBookSessionMap.map.remove(session.getId());
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String RequestJson = message.getPayload();
        Gson gson = new Gson();
        BSIList bsiList = gson.fromJson(RequestJson, BSIList.class);
        ArrayList<String> clientMarkets = CLIENTS.getOrDefault(session.getId(), new ArrayList<>());

        for (String market : bsiList.getMarketList()) {
            ArrayList<WebSocketSession> wsList = OrderBookSessionMap.map.getOrDefault(market, new ArrayList<>());
            clientMarkets.add(market);
            if(!wsList.contains(session)) wsList.add(session);  //session 중복 삽입 방지
            OrderBookSessionMap.map.put(market, wsList);
        }
        CLIENTS.put(session.getId(), clientMarkets);
    }
}
