package upbit_candle.candle.Entity.Result;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class OrderBookResult {
    private String market; // market

//    private Long total_ask_size;
//    private Long total_bid_size;
//    private List<Orderbook_unit> orderbook_units;
//    private Date date;

    private Double ask_price;
    private Double bid_price;
    private Double ask_size;
    private Double bid_size;

//    @Data
//    public class Orderbook_unit {
//        private Long ask_price;
//        private Long bid_price;
//        private Long ask_size;
//        private Long bid_size;
//    }
}
