package upbit_candle.candle.Entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity(name = "Conclusion")
@Data
@NoArgsConstructor
public class ConclusionEntity {
    @Id
    @GeneratedValue
    private Long id;

    private String code; // ex) KRW-BTC
    private Long trade_timestamp; // 체결 타임스탬프 (millisecond)
    private BigDecimal trade_price; // 체결 가격
    private BigDecimal trade_volume; // 체결량
    private BigDecimal real_price;
    public String ask_bid; // ASK 매수/ BID매도 구분
    private Date date;  // 체결 시각

    public ConclusionEntity(String name, Long trade_timestamp, BigDecimal trade_price, BigDecimal trade_volume, String ask_bid, String trade_date, String trade_time) throws ParseException {
        this.code = name;
        this.trade_timestamp = trade_timestamp;
        this.trade_price = trade_price;
        this.trade_volume = trade_volume;
        this.ask_bid = ask_bid;
        this.real_price = trade_price.multiply(trade_volume);
        String temp = trade_date + " " + trade_time;
        this.date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(temp);
    }
}
