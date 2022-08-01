package upbit_candle.candle.WebSocket;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class OnExecuteCoin {
    private static volatile OnExecuteCoin object = new OnExecuteCoin();
    public static Set<String> set = Collections.newSetFromMap(new ConcurrentHashMap<String, Boolean>());;

    private OnExecuteCoin(){}

    public OnExecuteCoin getSingleTon(){
        return object;
    }
}
