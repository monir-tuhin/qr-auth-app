package com.app.authentiScan.repository;


import com.app.authentiScan.enums.VerificationStatusEnum;
import com.app.authentiScan.models.QrData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface QrDataRepository extends JpaRepository<QrData, Long>, QuerydslPredicateExecutor<QrData> {

    @Query("select max(model.sl) from QrData model")
    Integer getMaxSlNo();

    Optional<QrData> findFirstByUid(String uid);

    Optional<QrData> findFirstByUidIgnoreCaseContaining(String uid);

    @Query("select model from QrData model where model.sl between ?1 and ?2")
    List<QrData> getQRListBySl(Integer from, Integer to);

    Optional<QrData> findTopByOrderByIdDesc();

}
