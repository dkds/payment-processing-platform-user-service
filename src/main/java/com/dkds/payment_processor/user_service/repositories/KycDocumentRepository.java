package com.dkds.payment_processor.user_service.repositories;

import com.dkds.payment_processor.user_service.entities.KycDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KycDocumentRepository extends JpaRepository<KycDocument, Long> {
}
