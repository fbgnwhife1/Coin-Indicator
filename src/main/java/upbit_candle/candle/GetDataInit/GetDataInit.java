package upbit_candle.candle.GetDataInit;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import upbit_candle.candle.Entity.MarketEntity;
import upbit_candle.candle.Repository.MarketRepository;
import upbit_candle.candle.Service.RunSocketService;
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

        public void openSocket() throws InterruptedException {
            List<String> list = ForTest.initTestData();
            runSocket.runSocket(list, 0L);
        }
    }
}
