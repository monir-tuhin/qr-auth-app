package com.app.authentiScan.repository;


import com.app.authentiScan.models.QrCodeValidation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface QrCodeValidationRepository extends JpaRepository<QrCodeValidation, Long>, QuerydslPredicateExecutor<QrCodeValidation> {

}
