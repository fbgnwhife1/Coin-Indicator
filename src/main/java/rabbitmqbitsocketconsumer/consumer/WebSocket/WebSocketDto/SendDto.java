package rabbitmqbitsocketconsumer.consumer.WebSocket.WebSocketDto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
public class SendDto {
    private String market;
    private BigDecimal real_price;
    private Date date;  // 체결 시각
    private BigDecimal trade_price; // 체결 가격
    private BigDecimal trade_volume; // 체결량

    public SendDto(String market, BigDecimal real_price, Date date, BigDecimal trade_price, BigDecimal trade_volume) {
        this.market = market;
        this.real_price = real_price;
        this.date = date;
        this.trade_price = trade_price;
        this.trade_volume = trade_volume;
    }
}
