package com.dkds.payment_processor.user_service.entities;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "\"kyc_document\"")
public class KycDocument {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private String fieldName;
    private String savedName;
    private String fileName;
}
