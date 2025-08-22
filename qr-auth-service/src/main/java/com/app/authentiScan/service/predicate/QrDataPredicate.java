package com.app.authentiScan.service.predicate;

import com.app.authentiScan.models.QQrData;
import com.app.authentiScan.payload.dto.search.QrVerificationHistorySearchDto;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

public class QrDataPredicate {
    private static final QQrData qQrData = QQrData.qrData;

    public static Predicate searchHistoryPredicate(QrVerificationHistorySearchDto searchDto) {
        BooleanBuilder builder = new BooleanBuilder();

        if (StringUtils.isNoneBlank(searchDto.getUid())) {
            builder.and(qQrData.uid.eq(searchDto.getUid()));
        }

        if (Objects.nonNull(searchDto.getVerificationStatusEnum())) {
            builder.and(qQrData.verificationScanStatusEnum.eq(searchDto.getVerificationStatusEnum()));
        }

        if (StringUtils.isNotBlank(searchDto.getSearchTerm())) {
            String searchTerm = searchDto.getSearchTerm().trim();
            BooleanBuilder searchBuilder = new BooleanBuilder();
            searchBuilder.or(qQrData.uid.containsIgnoreCase(searchTerm));
            searchBuilder.or(qQrData.verificationScanStatusEnum.stringValue().containsIgnoreCase(searchTerm));
            builder.and(searchBuilder);
        }

        return builder;
    }

}
