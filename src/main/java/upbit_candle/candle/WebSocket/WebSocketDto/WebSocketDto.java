package upbit_candle.candle.WebSocket.WebSocketDto;


import lombok.Data;
import upbit_candle.candle.Entity.ConclusionEntity;

import java.util.ArrayList;

@Data
public class WebSocketDto {
    private ArrayList<ConclusionEntity> list;
    private Long pivot;
}
