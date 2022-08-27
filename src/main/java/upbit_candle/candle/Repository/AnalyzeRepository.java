package upbit_candle.candle.Repository;

import upbit_candle.candle.Entity.Dto.RsiDataDto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface AnalyzeRepository {
    List<BigDecimal> findRSI(String market, Date start, Date end);
    List<RsiDataDto> findRSI2(String market, Date start, Date end);
}
