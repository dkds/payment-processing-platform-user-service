package com.dkds.payment_processor.user_service.dto;

import lombok.Data;

@Data
public class UserDto {
    private Long id;
    private String fullName;
    private String nationalId;
    private String address;
    private String phoneNumber;
    private String kycStatus;
}
