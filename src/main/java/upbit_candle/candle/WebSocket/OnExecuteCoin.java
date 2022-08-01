package upbit_candle.candle.WebSocket;

import java.util.HashSet;

public class OnExecuteCoin {
    private static volatile OnExecuteCoin object = new OnExecuteCoin();
    public static HashSet<String> set = new HashSet<>();

    private OnExecuteCoin(){}

    public OnExecuteCoin getSingleTon(){
        return object;
    }
}
