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

    public SendDto(String market, BigDecimal real_price, Date date) {
        this.market = market;
        this.real_price = real_price;
        this.date = date;
    }
}
