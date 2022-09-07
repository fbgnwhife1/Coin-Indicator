package rabbitmqbitsocketconsumer.consumer.WebSocket;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class UpbitSocketMap {
    private static final UpbitSocketMap object = new UpbitSocketMap();
//    public static Set<String> set = Collections.newSetFromMap(new ConcurrentHashMap<String, Boolean>());;

    private UpbitSocketMap(){}

    public UpbitSocketMap getSingleTon(){
        return object;
    }
}
