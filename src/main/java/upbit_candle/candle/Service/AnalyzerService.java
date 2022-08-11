package upbit_candle.candle.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import upbit_candle.candle.Analyze.RSI.RSI;
import upbit_candle.candle.Entity.ConclusionEntity;
import upbit_candle.candle.Entity.Result.Conclusion;
import upbit_candle.candle.Repository.ConclusionRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AnalyzerService {
    private final ConclusionRepository conclusionRepository;

    public double RSI(String code, Integer period){
        RSI rsi = new RSI(Integer.parseInt(String.valueOf(period)));
        Date startDatetime = new Date(System.currentTimeMillis()-24*60*60*1000*period);
        Date endDatetime = new Date();

        List<ConclusionEntity> conclusionList = conclusionRepository.findAllByDateBetween(code, startDatetime, endDatetime);
        double[] prizes = new double[conclusionList.size()];
        for (int i = 0; i < prizes.length; i++) {
            prizes[i] = conclusionList.get(i).getReal_price().doubleValue();
        }

        double[] result = rsi.count(prizes);

        return result[period-1];
    }
}
