package upbit_candle.candle.Repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import upbit_candle.candle.Entity.ConclusionEntity;
import upbit_candle.candle.Entity.Result.Conclusion;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository
public interface ConclusionRepository extends JpaRepository<ConclusionEntity, String> {
    List<ConclusionEntity> findByCode(String code);

    @Query(value = "select r from Conclusion r where r.trade_timestamp > :time and r.code = :code")
    List<ConclusionEntity> findByCodeAndTime(String code, long time);

    @Query(value = "select r" +
            " from Conclusion r" +
            " where r.code = :code " +
            " and r.date between :start and :end" +
            " group by r.date")
    List<ConclusionEntity> findAllByDateBetween(String code, Date start, Date end);
}
