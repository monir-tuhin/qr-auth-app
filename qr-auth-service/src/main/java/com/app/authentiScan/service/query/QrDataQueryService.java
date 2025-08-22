package com.app.authentiScan.service.query;

import com.app.authentiScan.models.QQrData;
import com.app.authentiScan.models.QrData;
import com.app.authentiScan.payload.dto.search.QrVerificationHistorySearchDto;
import com.app.authentiScan.payload.response.QrCodeSummaryResponse;
import com.app.authentiScan.service.predicate.QrDataPredicate;
import com.app.authentiScan.utils.DateTimeFormatterUtilService;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class QrDataQueryService {

    private final EntityManager entityManager;

    public QrCodeSummaryResponse getQrCodeSummary() {
        final QQrData qQrData = QQrData.qrData;
        JPAQuery<QrCodeSummaryResponse> query = new JPAQuery<>(entityManager);
        JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);


        QrCodeSummaryResponse result = query.select(Projections.constructor(
                QrCodeSummaryResponse.class,
                qQrData.count(),
                new CaseBuilder()
                        .when(qQrData.createdAt.between(
                                LocalDate.now().atStartOfDay(),
                                LocalDate.now().plusDays(1).atStartOfDay().minusNanos(1)
                        )).then(1L).otherwise(0L).sum(),
                qQrData.createdAt.max()
        )).from(qQrData).fetchOne();

        if (result != null && result.getLastQrCodeCreatedDate() != null) {
            // latest's count
            LocalDateTime latestDateTime = result.getLastQrCodeCreatedDate();
            LocalDateTime fifteenMinutesAgo = latestDateTime.minusMinutes(15);

            Long latestCount = queryFactory
                    .select(qQrData.count())
                    .from(qQrData)
                    .where(qQrData.createdAt.between(fifteenMinutesAgo, latestDateTime))
                    .fetchOne();
            result.setLastQrCodeCount(latestCount);
        }

        if (Objects.nonNull(result)) {
            Optional.ofNullable(result.getLastQrCodeCreatedDate())
                    .map(DateTimeFormatterUtilService::localDateTimeToDateTimeString)
                    .ifPresent(result::setLastQrCodeCreatedDateFormatted);
        }

        return result;
    }

    public Page<QrData> searchPage(QrVerificationHistorySearchDto searchDto) {
        Pageable pageable = PageRequest.of(searchDto.getPage(), searchDto.getSize());
        QQrData qQrData = QQrData.qrData;
        JPAQuery<QrData> query = new JPAQuery<QrData>(entityManager)
                .from(qQrData)
                .where(QrDataPredicate.searchHistoryPredicate(searchDto))
                .orderBy(qQrData.createdAt.desc())
                .limit(searchDto.getSize())
                .offset(searchDto.getPage());
        return new PageImpl<>(query.fetch(), pageable, query.fetchCount());
    }

}
