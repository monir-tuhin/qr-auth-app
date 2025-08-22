package com.app.authentiScan.service.predicate;

import com.app.authentiScan.models.QQrCodeValidation;
import com.app.authentiScan.payload.dto.search.QrValidationSearchDto;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

public class QrCodeValidationPredicate {
    private static final QQrCodeValidation qQrCodeValidation = QQrCodeValidation.qrCodeValidation;

    public static Predicate searchQrValidation(QrValidationSearchDto searchDto) {
        BooleanBuilder builder = new BooleanBuilder();

        if (StringUtils.isNoneBlank(searchDto.getUid())) {
            builder.and(qQrCodeValidation.uid.eq(searchDto.getUid()));
        }
        if (Objects.nonNull(searchDto.getValidationMethodsEnum())) {
            builder.and(qQrCodeValidation.validationMethodsEnum.eq(searchDto.getValidationMethodsEnum()));
        }
        if (Objects.nonNull(searchDto.getValidationStatusEnum())) {
            builder.and(qQrCodeValidation.validationStatusEnum.eq(searchDto.getValidationStatusEnum()));
        }
        if (StringUtils.isNoneBlank(searchDto.getMobileNumber())) {
            builder.and(qQrCodeValidation.mobileNumber.eq(searchDto.getMobileNumber()));
        }
        if (StringUtils.isNoneBlank(searchDto.getMonth())) {
            builder.and(qQrCodeValidation.month.eq(searchDto.getMonth()));
        }
        if (Objects.nonNull(searchDto.getWeekEnum())) {
            builder.and(qQrCodeValidation.weekEnum.eq(searchDto.getWeekEnum()));
        }
        if (Objects.nonNull(searchDto.getQuarterEnum())) {
            builder.and(qQrCodeValidation.quarterEnum.eq(searchDto.getQuarterEnum()));
        }
        if (Objects.nonNull(searchDto.getYear())) {
            builder.and(qQrCodeValidation.year.eq(searchDto.getYear()));
        }

        if (StringUtils.isNotBlank(searchDto.getSearchTerm())) {
            String searchTerm = searchDto.getSearchTerm().trim();
            BooleanBuilder searchBuilder = new BooleanBuilder();
            searchBuilder.or(qQrCodeValidation.uid.containsIgnoreCase(searchTerm));
            searchBuilder.or(qQrCodeValidation.mobileNumber.containsIgnoreCase(searchTerm));
            builder.and(searchBuilder);
        }

        return builder;
    }

}
