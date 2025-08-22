package com.app.authentiScan.service.query;

import com.app.authentiScan.models.QQrCodeValidation;
import com.app.authentiScan.models.QrCodeValidation;
import com.app.authentiScan.payload.dto.search.QrValidationSearchDto;
import com.app.authentiScan.payload.response.QrStatusSummaryResponse;
import com.app.authentiScan.service.predicate.QrCodeValidationPredicate;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import javax.persistence.EntityManager;;import java.util.List;

@Service
@RequiredArgsConstructor
public class QrCodeValidationQueryService {

    private final EntityManager entityManager;

    public List<QrCodeValidation> searchByUid(String uid) {
        QQrCodeValidation qQrCodeValidation = QQrCodeValidation.qrCodeValidation;
        return new JPAQuery<QrCodeValidation>(entityManager)
                .from(qQrCodeValidation)
                .where(qQrCodeValidation.uid.eq(uid))
                .fetch();
    }

    public List<QrCodeValidation> searchQrValidation(QrValidationSearchDto searchDto) {
        QQrCodeValidation qQrCodeValidation = QQrCodeValidation.qrCodeValidation;
        return new JPAQuery<QrCodeValidation>(entityManager)
                .from(qQrCodeValidation)
                .where(QrCodeValidationPredicate.searchQrValidation(searchDto))
                .fetch();
    }

    public List<QrStatusSummaryResponse> searchQrStatusSummaryQuarterly(QrValidationSearchDto searchDto) {
        QQrCodeValidation qQrCodeValidation = QQrCodeValidation.qrCodeValidation;
        JPAQuery<QrStatusSummaryResponse> query = new JPAQuery<>(entityManager);

        List<QrStatusSummaryResponse> results = query.select(Projections.constructor(
                        QrStatusSummaryResponse.class,
                        qQrCodeValidation.validationStatusEnum.stringValue(),
                        qQrCodeValidation.validationStatusEnum.count()
                ))
                .from(qQrCodeValidation)
                .where(QrCodeValidationPredicate.searchQrValidation(searchDto))
                .groupBy(qQrCodeValidation.validationStatusEnum, qQrCodeValidation.quarterEnum, qQrCodeValidation.year)
                .fetch();

        return results;
    }

    public List<QrStatusSummaryResponse> searchQrStatusSummaryMonthly(QrValidationSearchDto searchDto) {
        QQrCodeValidation qQrCodeValidation = QQrCodeValidation.qrCodeValidation;
        JPAQuery<QrStatusSummaryResponse> query = new JPAQuery<>(entityManager);

        List<QrStatusSummaryResponse> results = query.select(Projections.constructor(
                        QrStatusSummaryResponse.class,
                        qQrCodeValidation.validationStatusEnum.stringValue(),
                        qQrCodeValidation.validationStatusEnum.count()
                ))
                .from(qQrCodeValidation)
                .where(QrCodeValidationPredicate.searchQrValidation(searchDto))
                .groupBy(qQrCodeValidation.validationStatusEnum, qQrCodeValidation.month, qQrCodeValidation.year)
                .fetch();

        return results;
    }

    public List<QrStatusSummaryResponse> searchQrMethodSummaryQuarterly(QrValidationSearchDto searchDto) {
        QQrCodeValidation qQrCodeValidation = QQrCodeValidation.qrCodeValidation;
        JPAQuery<QrStatusSummaryResponse> query = new JPAQuery<>(entityManager);

        List<QrStatusSummaryResponse> results = query.select(Projections.constructor(
                        QrStatusSummaryResponse.class,
                        qQrCodeValidation.validationMethodsEnum.stringValue(),
                        qQrCodeValidation.validationMethodsEnum.count()
                ))
                .from(qQrCodeValidation)
                .where(QrCodeValidationPredicate.searchQrValidation(searchDto))
                .groupBy(qQrCodeValidation.validationMethodsEnum, qQrCodeValidation.quarterEnum, qQrCodeValidation.year)
                .fetch();

        return results;
    }

    public List<QrStatusSummaryResponse> searchQrMethodSummaryMonthly(QrValidationSearchDto searchDto) {
        QQrCodeValidation qQrCodeValidation = QQrCodeValidation.qrCodeValidation;
        JPAQuery<QrStatusSummaryResponse> query = new JPAQuery<>(entityManager);

        List<QrStatusSummaryResponse> results = query.select(Projections.constructor(
                        QrStatusSummaryResponse.class,
                        qQrCodeValidation.validationMethodsEnum.stringValue(),
                        qQrCodeValidation.validationMethodsEnum.count()
                ))
                .from(qQrCodeValidation)
                .where(QrCodeValidationPredicate.searchQrValidation(searchDto))
                .groupBy(qQrCodeValidation.validationMethodsEnum, qQrCodeValidation.month, qQrCodeValidation.year)
                .fetch();

        return results;
    }

}
