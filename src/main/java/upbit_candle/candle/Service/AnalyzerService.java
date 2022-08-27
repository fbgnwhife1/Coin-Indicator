package upbit_candle.candle.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import upbit_candle.candle.Analyze.FearAndGreed.FearAndGreed;
import upbit_candle.candle.Analyze.RSI.RSI;
import upbit_candle.candle.ApiConnect.FearAndGreedApiConnect;
import upbit_candle.candle.ApiConnect.OrderBookConnect;
import upbit_candle.candle.Entity.Dto.RsiDataDto;
import upbit_candle.candle.Entity.Result.OrderBookResult;
import upbit_candle.candle.Repository.AnalyzeRepository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AnalyzerService {
    private final AnalyzeRepository analyzeRepository;
    private OrderBookConnect orderBookService = new OrderBookConnect();
    private FearAndGreedApiConnect fearAndGreedApiConnect = new FearAndGreedApiConnect();

    public double RSI(String code, Integer period) {
        RSI rsi = new RSI(Integer.parseInt(String.valueOf(period)));
        Date startDatetime = new Date(System.currentTimeMillis() - 24 * 60 * 60 * 1000 * period);
        Date endDatetime = new Date();

        List<RsiDataDto> rsiData = analyzeRepository.findRSI2(code, startDatetime, endDatetime);
        List<BigDecimal> conclusionList = RsiDataDto.transData(rsiData, period);
        double[] prizes = new double[conclusionList.size()];
        for (int i = 0; i < prizes.length; i++) {
            prizes[i] = conclusionList.get(i).doubleValue();
        }

        return rsi.count(prizes);
    }

    public double[] BSI(String market){
        List<OrderBookResult> orderBookList = orderBookService.getOrderBook(market);

        double[] bsi = new double[orderBookList.size()];

        for(int i = 0; i < bsi.length; i++) {
            bsi[i] = (double)orderBookList.get(i).getBid_size() / (orderBookList.get(i).getAsk_size() + orderBookList.get(i).getBid_size());
        }

        return bsi;
    }

    public FearAndGreed getFnG(){
        FearAndGreed fgIndex = fearAndGreedApiConnect.getFGIndex();
        return fgIndex;
    }
}
