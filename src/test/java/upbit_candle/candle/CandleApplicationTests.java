package upbit_candle.candle;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import upbit_candle.candle.trade_search.MarketSearchSearvice;
import upbit_candle.candle.trade_search.TickService;

import java.io.IOException;
import java.math.BigDecimal;

@SpringBootTest
class CandleApplicationTests {

    @Test
    void TickService() {
        BigDecimal p1 = new BigDecimal("1_000_000");
        BigDecimal c = new BigDecimal("5");

        if(c.compareTo(p1) > 0) System.out.println(1);
        else System.out.println(-1);

    }


}
