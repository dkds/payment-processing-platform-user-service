package com.dkds.payment_processor.user_service.entities;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "\"user\"")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fullName;
    private String nationalId;
    private String address;
    private String phoneNumber;
    private String kycStatus;

}
