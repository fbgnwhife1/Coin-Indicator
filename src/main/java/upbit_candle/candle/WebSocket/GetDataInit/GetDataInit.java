package upbit_candle.candle.WebSocket.GetDataInit;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import upbit_candle.candle.Entity.MarketEntity;
import upbit_candle.candle.Repository.MarketRepository;
import upbit_candle.candle.WebSocket.RunSocketService;
import upbit_candle.candle.Service.forTest.ForTest;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class GetDataInit {
    private final InitService initService;

    @PostConstruct
    public void init() throws Exception {
        initService.openSocket();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {
        private final RunSocketService runSocket;
        private final MarketRepository marketRepository;

        public void openSocket() throws InterruptedException {
//            List<String> list = ForTest.initTestData();
//            runSocket.runSocket(list, 0L);
            List<MarketEntity> all = marketRepository.findAll();
            List<String> marketList = new ArrayList<>();

            for (MarketEntity market : all) {
                marketList.add(market.getMarket());

                if(marketList.size() == 15){
                    runSocket.runSocket(marketList, 0L);
                    marketList.clear();
                    Thread.sleep(2000);
                }
            }

//            for (String market : list) {
//                marketList.add(market);
//
//                if (marketList.size() == 4) {
//                    runSocket.runSocket(marketList, 0L);
//                    marketList.clear();
//                    Thread.sleep(2000);
//                    cnt++;
//                }
//
//                if (cnt == 3) {
//                    break;
//                }
//            }

            Thread.sleep(2000);
            if(marketList.size() > 0){
                runSocket.runSocket(marketList, 0L);
            }
        }
    }
}
