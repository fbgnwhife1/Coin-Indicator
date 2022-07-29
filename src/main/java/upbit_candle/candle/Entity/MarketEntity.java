package upbit_candle.candle.Entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "market")
@Data
@NoArgsConstructor
public class MarketEntity {

    @Id
    String market;
    String korean_name;
    String english_name;

    public MarketEntity(String market, String korean_name, String english_name) {
        this.market = market;
        this.korean_name = korean_name;
        this.english_name = english_name;
    }
}
