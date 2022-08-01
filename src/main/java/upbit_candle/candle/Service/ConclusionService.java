package upbit_candle.candle.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import upbit_candle.candle.Entity.ConclusionEntity;
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

    


}
