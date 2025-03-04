package com.dkds.payment_processor.user_service.services;

import com.dkds.payment_processor.user_service.dto.FileDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface StorageService {
    List<FileDto> store(MultipartFile[] files);
}
