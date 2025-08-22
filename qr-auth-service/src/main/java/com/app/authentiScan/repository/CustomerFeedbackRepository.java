package com.app.authentiScan.repository;

import com.app.authentiScan.models.CustomerFeedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface CustomerFeedbackRepository extends JpaRepository<CustomerFeedback, Long> {

    Optional<CustomerFeedback> findByProductUid(String productUid);
}
