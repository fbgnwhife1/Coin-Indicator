package upbit_candle.candle.Repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import upbit_candle.candle.Entity.ConclusionEntity;

import java.util.List;

@Repository
public interface ConclusionRepository extends JpaRepository<ConclusionEntity, String> {
    List<ConclusionEntity> findByCode(String code);

    @Query(value = "select r from Conclusion r where r.trade_timestamp > :time and r.code = :code", nativeQuery = false)
    List<ConclusionEntity> findByCodeAndTime(String code, long time);

}
