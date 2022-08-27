package upbit_candle.candle.Entity.Dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class RsiDataDto {

    private int date;
    private BigDecimal sum;

    @QueryProjection
    public RsiDataDto(int date, BigDecimal sum) {
        this.date = date;
        this.sum = sum;
    }

    public static List<BigDecimal> transData(List<RsiDataDto> rsi, int period){
        List<BigDecimal> list = new ArrayList<>();
        int idx = 0;
        int mDay = period;
        for(int i = 0; i < period; i++) {
            int date = LocalDateTime.now().minusDays(mDay--).getDayOfYear();
            if(rsi.size()!=0 && rsi.size()>idx && rsi.get(idx).getDate()==date){
                list.add(rsi.get(idx++).getSum());
            }else{
                list.add(new BigDecimal(0));
            }
        }

        return list;
    }
}
