package upbit_candle.candle.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import upbit_candle.candle.Entity.MarketEntity;

import java.util.List;

@Repository
public interface MarketRepository extends JpaRepository<MarketEntity, String> {
    List<MarketEntity> findByMarketContaining(String code);
}
