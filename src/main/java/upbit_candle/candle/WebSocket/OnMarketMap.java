package upbit_candle.candle.WebSocket;

import org.springframework.web.socket.WebSocketSession;

import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class OnMarketMap {


    private static volatile OnMarketMap object = new OnMarketMap();
    public static Map<String, ArrayList<WebSocketSession>> map = new ConcurrentHashMap<>();

    private OnMarketMap(){}

    public OnMarketMap getSingleTon(){
        return object;
    }
}
