package rabbitmqbitsocketconsumer.consumer.WebSocket.WebSocketDto;


import lombok.Data;
import rabbitmqbitsocketconsumer.consumer.Entity.ConclusionEntity;

import java.util.ArrayList;

@Data
public class WebSocketDto {
    private ArrayList<String> list;
    private Long pivot;
}
