package upbit_candle.candle.WebSocket;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.w3c.dom.Text;
import upbit_candle.candle.Entity.ConclusionEntity;
import upbit_candle.candle.Service.WebSocketService;
import upbit_candle.candle.WebSocketDto.SendDto;
import upbit_candle.candle.WebSocketDto.WebSocketDto;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;

@Component
@RequiredArgsConstructor
public class WebSocketHandler extends TextWebSocketHandler {

    private static final ConcurrentHashMap<String, WebSocketSession> CLIENTS = new ConcurrentHashMap<String, WebSocketSession>();
    private final WebSocketService socketService;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        CLIENTS.put(session.getId(), session);
//
//        session.sendMessage(new TextMessage("123"));
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        CLIENTS.remove(session.getId());
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String RequestJson = message.getPayload();
        Gson gson = new Gson();
        Timer timer = new Timer();
        TimerTask m_task = new TimerTask() {
            @SneakyThrows
            @Override
            public void run() {
                try {
                    WebSocketDto list = gson.fromJson(RequestJson, WebSocketDto.class);
                    List<SendDto> sendDtos = socketService.sendData(list);
                    session.sendMessage(new TextMessage(gson.toJson(sendDtos)));
                }catch (Exception e){
                    session.sendMessage(new TextMessage("형식 오류입니다"));
                    return;
                }
            }
        };

        try {
            timer.scheduleAtFixedRate(m_task, 0l,5000);
        }catch (Exception e){
            session.sendMessage(new TextMessage("형식 오류입니다"));
            return;
        }
    }



}
