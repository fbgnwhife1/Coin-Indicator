package upbit_candle.candle.Repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import upbit_candle.candle.Entity.ConclusionEntity;

import java.util.List;

@Repository
public interface ConclusionRepository extends JpaRepository<ConclusionEntity, String> {
}
