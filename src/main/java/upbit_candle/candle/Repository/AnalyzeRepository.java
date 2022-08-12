package upbit_candle.candle.Repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface AnalyzeRepository {
    List<BigDecimal> findRSI(String market, Date start, Date end);
}
