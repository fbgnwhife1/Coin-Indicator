package rabbitmqbitsocketconsumer.consumer.Listener;

import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import rabbitmqbitsocketconsumer.consumer.DTO.BSI_RBIDto;
import rabbitmqbitsocketconsumer.consumer.Entity.ConclusionEntity;
import rabbitmqbitsocketconsumer.consumer.Repository.ConclusionRepository;
import rabbitmqbitsocketconsumer.consumer.WebSocket.MarketAndSessionMap;
import rabbitmqbitsocketconsumer.consumer.WebSocket.OrderBookSessionMap;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;

@Component
@RequiredArgsConstructor
public class OrderBookListener {

    private final Gson gson = new Gson();
    private final String queueName = "q.bsi";
    private final Logger log = LoggerFactory.getLogger(BSI_RBIDto.class);

    @RabbitListener(queues = queueName)
    public void receive(BSI_RBIDto dto) throws IOException {
        log.info("{}", dto);
        ArrayList<WebSocketSession> list = OrderBookSessionMap.map.computeIfAbsent(dto.getMarket(), k -> new ArrayList<>());
        if(list.size() != 0){
            for (WebSocketSession ws : list) {
                if(ws == null) continue;
                ws.sendMessage(new TextMessage(gson.toJson(dto)));
            }
        }
    }
}
