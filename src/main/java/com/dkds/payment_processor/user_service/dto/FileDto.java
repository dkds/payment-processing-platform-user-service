package com.dkds.payment_processor.user_service.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class FileDto {
    private String fieldName;
    private String fileName;
    private String savedName;
}
