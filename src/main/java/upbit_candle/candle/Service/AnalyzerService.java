package upbit_candle.candle.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import upbit_candle.candle.Analyze.RSI.RSI;
import upbit_candle.candle.Entity.Result.OrderBookResult;
import upbit_candle.candle.Repository.AnalyzeRepository;
import upbit_candle.candle.Repository.ConclusionRepository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AnalyzerService {
    private final ConclusionRepository conclusionRepository;
    private final AnalyzeRepository analyzeRepository;
    private OrderBookService orderBookService = new OrderBookService();

    public double RSI(String code, Integer period) {
        RSI rsi = new RSI(Integer.parseInt(String.valueOf(period)));
        Date startDatetime = new Date(System.currentTimeMillis() - 24 * 60 * 60 * 1000 * period);
        Date endDatetime = new Date();

//        List<ConclusionEntity> conclusionList = conclusionRepository.findAllByDateBetween(code, startDatetime, endDatetime);
//        double[] prizes = new double[conclusionList.size()];
//        for (int i = 0; i < prizes.length; i++) {
//            prizes[i] = conclusionList.get(i).getReal_price().doubleValue();
//        }

        List<BigDecimal> conclusionList = analyzeRepository.findRSI(code, startDatetime, endDatetime);
        double[] prizes = new double[conclusionList.size()];
        for (int i = 0; i < prizes.length; i++) {
            prizes[i] = conclusionList.get(i).doubleValue();
        }

        double result = rsi.count(prizes);
        return result;
    }

    public double[] BSI(String market){
        List<OrderBookResult> orderBookList = orderBookService.getOrderBook(market);

        double[] bsi = new double[orderBookList.size()];

        for(int i = 0; i < bsi.length; i++) {
            bsi[i] = (double)orderBookList.get(i).getBid_size() / (orderBookList.get(i).getAsk_size() + orderBookList.get(i).getBid_size());
        }

        return bsi;
    }
}
