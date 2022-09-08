package rabbitmqbitsocketconsumer.consumer.WebSocket;

import org.springframework.web.socket.WebSocketSession;

import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MarketAndSessionMap {


    private static final MarketAndSessionMap object = new MarketAndSessionMap();
    public static Map<String, ArrayList<WebSocketSession>> map = new ConcurrentHashMap<>();
    public static Map<String, Long> pivotMap = new ConcurrentHashMap<String, Long>();

    private MarketAndSessionMap(){}

    public static MarketAndSessionMap getSingleTon(){
        return object;
    }
}
