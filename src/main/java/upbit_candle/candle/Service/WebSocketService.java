package upbit_candle.candle.Service;

import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import upbit_candle.candle.Entity.ConclusionEntity;
import upbit_candle.candle.Repository.ConclusionRepository;
import upbit_candle.candle.WebSocketDto.SendDto;
import upbit_candle.candle.WebSocketDto.WebSocketDto;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WebSocketService {

    private final ConclusionRepository repository;
    private Gson gson = new Gson();

    public List<SendDto> sendData(WebSocketDto list){
        List<ConclusionEntity> marketList = list.getList();
        List<SendDto> resultList = new ArrayList<>();

        for(ConclusionEntity code : marketList){
            List<ConclusionEntity> all = repository.findByCodeAndTime(code.getCode(), System.currentTimeMillis()-5000);
            for (ConclusionEntity conclusionEntity : all) {
                SendDto temp = new SendDto(conclusionEntity.getCode(), conclusionEntity.getReal_price(), conclusionEntity.getDate());
                resultList.add(temp);
            }
        }

        return resultList;
    }
}
