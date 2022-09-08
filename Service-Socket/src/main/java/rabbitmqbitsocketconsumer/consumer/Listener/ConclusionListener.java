package rabbitmqbitsocketconsumer.consumer.Listener;

import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import rabbitmqbitsocketconsumer.consumer.Entity.ConclusionEntity;
import rabbitmqbitsocketconsumer.consumer.Repository.ConclusionRepository;
import rabbitmqbitsocketconsumer.consumer.WebSocket.MarketAndSessionMap;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;

@Component
@RequiredArgsConstructor
public class ConclusionListener {
    private final Gson gson = new Gson();
    private final String queueName = "market";
    private final Logger log = LoggerFactory.getLogger(ConclusionEntity.class);
    private final ConclusionRepository conclusionRepository;

    @RabbitListener(queues = queueName)
    public void receive(ConclusionEntity cResult) throws IOException {
        log.info("{}", cResult);
        conclusionRepository.save(cResult);
        ArrayList<WebSocketSession> list = MarketAndSessionMap.map.computeIfAbsent(cResult.getCode(), k -> new ArrayList<>());
        if(list.size() != 0){
            for (WebSocketSession ws : list) {
                if(ws == null) continue;
                if(BigDecimal.valueOf(MarketAndSessionMap.pivotMap.getOrDefault(ws.getId(), 0L))
                        .compareTo(cResult.getReal_price()) > 0) continue;

                ws.sendMessage(new TextMessage(gson.toJson(cResult)));
            }
        }
    }
}
