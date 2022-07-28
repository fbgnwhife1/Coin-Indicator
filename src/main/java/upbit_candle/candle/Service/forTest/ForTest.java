package upbit_candle.candle.Service.forTest;

import java.util.ArrayList;

public class ForTest {

    public static ArrayList<String> initTestData(){
        ArrayList<String> list = new ArrayList<>();

        list.add("BTC-DOGE");
        list.add("KRW-DOGE");
        list.add("USDT-DOGE");
        list.add("KRW-BTC");
        list.add("USDT-BTC");
        list.add("BTC-SOL");
        list.add("KRW-SOL");
        list.add("BTC-SOLVE");
        list.add("BTC-ETH");
        list.add("KRW-ETH");
        list.add("USDT-ETH");

        return list;
    }
}
