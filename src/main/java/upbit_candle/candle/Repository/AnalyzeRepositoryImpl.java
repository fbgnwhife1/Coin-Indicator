package upbit_candle.candle.Repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import static upbit_candle.candle.Entity.QConclusionEntity.*;

@RequiredArgsConstructor
@Repository
public class AnalyzeRepositoryImpl implements AnalyzeRepository{
    private final JPAQueryFactory queryFactory;

    @Override
    public List<BigDecimal> findRSI(String market, Date start, Date end) {
        return queryFactory
                .select(conclusionEntity.real_price)
                .from(conclusionEntity)
                .where(conclusionEntity.code.eq(market)
                        .and(conclusionEntity.date.between(start, end)))
                .groupBy(conclusionEntity.date.dayOfYear())
                .fetch();
    }
}
