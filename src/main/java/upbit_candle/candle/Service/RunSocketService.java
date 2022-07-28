package upbit_candle.candle.Service;

import lombok.RequiredArgsConstructor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import upbit_candle.candle.Market.Market;
import upbit_candle.candle.Market.Result.Conclusion;
import upbit_candle.candle.Repository.ConclusionRepository;
import upbit_candle.candle.Repository.MarketRepository;
import upbit_candle.candle.Service.ConclusionService;
import upbit_candle.candle.Service.forTest.ForTest;
import upbit_candle.candle.WebSocket.WsListener;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RunSocketService {

    private final WsListener webSocketListener;
    private final MarketRepository marketRepository;

    public void runSocket() throws InterruptedException{
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("wss://api.upbit.com/websocket/v1")
                .build();

//        WsListener webSocketListener = new WsListener();
//        webSocketListener.setParameter(Conclusion.trade, List.of("KRW-BTC"));

        List<Market> all = marketRepository.findAll();
//        ArrayList<String> list = new ArrayList<>();
//        for(int i = 0; i < 15; i++){
//                list.add(all.get(i).getMarket());
//        }

        ArrayList<String> list = ForTest.initTestData();
        webSocketListener.setParameter(Conclusion.trade, list);

        client.newWebSocket(request, webSocketListener);

        client.dispatcher().executorService().shutdown();

    }
}
