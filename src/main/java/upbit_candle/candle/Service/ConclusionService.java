package upbit_candle.candle.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import upbit_candle.candle.Market.ConclusionEntity;
import upbit_candle.candle.Repository.ConclusionRepository;
import upbit_candle.candle.Repository.MarketRepository;

@Service
@RequiredArgsConstructor
public class ConclusionService {

    private final ConclusionRepository conclusionRepository;
    private final MarketRepository marketRepository;

    public void save(ConclusionEntity cResult) {
        conclusionRepository.save(cResult);
    }

//    public void runSocket() throws Exception{
//        List<Market> all = marketRepository.findAll();
//
//        ArrayList<String> list = new ArrayList<>();
//        for(int i = 0; i < 15; i++){
//                list.add(all.get(i).getMarket());
//        }
//
//        if(!list.isEmpty()){
//            runSocket.run(list);
//        }
//    }

}
