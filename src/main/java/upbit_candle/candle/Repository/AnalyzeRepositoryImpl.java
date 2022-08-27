package upbit_candle.candle.Repository;

import com.querydsl.core.types.Expression;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import upbit_candle.candle.Entity.Dto.QRsiDataDto;
import upbit_candle.candle.Entity.Dto.RsiDataDto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import static upbit_candle.candle.Entity.QConclusionEntity.*;

@RequiredArgsConstructor
@Repository
public class AnalyzeRepositoryImpl implements AnalyzeRepository{
    private final JPAQueryFactory queryFactory;

    /*
    Date start ~ end 지정마켓
     */
    @Override
    public List<BigDecimal> findRSI(String market, Date start, Date end) {
        return queryFactory
                .select(conclusionEntity.real_price.sum())
                .from(conclusionEntity)
                .where(conclusionEntity.code.eq(market)
                        .and(conclusionEntity.date.between(start, end)))
                .groupBy(conclusionEntity.date.dayOfYear())
                .orderBy(conclusionEntity.date.asc())
                .fetch();
    }

    @Override
    public List<RsiDataDto> findRSI2(String market, Date start, Date end) {
        return queryFactory
                .select(new QRsiDataDto(conclusionEntity.date.dayOfYear(), conclusionEntity.real_price.sum()))
                .from(conclusionEntity)
                .where(conclusionEntity.code.eq(market)
                        .and(conclusionEntity.date.between(start, end)))
                .groupBy(conclusionEntity.date.dayOfYear())
                .orderBy(conclusionEntity.date.asc())
                .fetch();
    }
}
