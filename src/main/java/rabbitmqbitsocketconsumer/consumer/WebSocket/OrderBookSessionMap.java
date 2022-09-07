package rabbitmqbitsocketconsumer.consumer.WebSocket;

import org.springframework.web.socket.WebSocketSession;

import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class OrderBookSessionMap {
    private static final OrderBookSessionMap object = new OrderBookSessionMap();
    public static Map<String, ArrayList<WebSocketSession>> map = new ConcurrentHashMap<>();

    private OrderBookSessionMap(){}

    public OrderBookSessionMap getSingleTon(){
        return object;
    }
}
