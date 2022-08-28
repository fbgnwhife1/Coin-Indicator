package upbit_candle.candle.WebSocket;

import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import upbit_candle.candle.Entity.ConclusionEntity;
import upbit_candle.candle.WebSocket.WebSocketDto.WebSocketDto;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

@Component
@RequiredArgsConstructor
public class WebSocketHandler extends TextWebSocketHandler {

    private static final ConcurrentHashMap<String, ArrayList<String>> CLIENTS = new ConcurrentHashMap<String,  ArrayList<String>>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {

    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        ArrayList<String> marketList = CLIENTS.get(session.getId());
        if(marketList!=null && !marketList.isEmpty()) {
            for (String market : marketList) {
                MarketAndSessionMap.map.get(market).remove(session);
            }
        }

        CLIENTS.remove(session.getId());
        MarketAndSessionMap.pivotMap.remove(session.getId());
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String RequestJson = message.getPayload();
        Gson gson = new Gson();
        WebSocketDto list = gson.fromJson(RequestJson, WebSocketDto.class);
        MarketAndSessionMap.pivotMap.put(session.getId(), list.getPivot() != null ? list.getPivot() : 0L);  //피벗 요청 마지막 기준
        ArrayList<ConclusionEntity> marketList = list.getList();
        ArrayList<String> clientMarkets = CLIENTS.getOrDefault(session.getId(), new ArrayList<>());

        for (ConclusionEntity conclusionEntity : marketList) {
            ArrayList<WebSocketSession> wsList = MarketAndSessionMap.map.getOrDefault(conclusionEntity.getCode(), new ArrayList<>());
            clientMarkets.add(conclusionEntity.getCode());
            if(!wsList.contains(session)) wsList.add(session);  //session 중복 삽입 방지
            MarketAndSessionMap.map.put(conclusionEntity.getCode(), wsList);
        }
        CLIENTS.put(session.getId(), clientMarkets);

//        Timer timer = new Timer();
//        TimerTask m_task = new TimerTask() {
//            @SneakyThrows
//            @Override
//            public void run() {
//                try {
//                    WebSocketDto list = gson.fromJson(RequestJson, WebSocketDto.class);
//                    List<SendDto> sendDtos = socketService.sendData(list);
//                    session.sendMessage(new TextMessage(gson.toJson(sendDtos)));
//                }catch (Exception e){
//                    session.sendMessage(new TextMessage("형식 오류입니다"));
//                    return;
//                }
//            }
//        };
//
//        try {
//            timer.scheduleAtFixedRate(m_task, 0l,5000);
//        }catch (Exception e){
//            session.sendMessage(new TextMessage("형식 오류입니다"));
//            return;
//        }
    }


}
