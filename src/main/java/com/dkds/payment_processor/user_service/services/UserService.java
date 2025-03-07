package com.dkds.payment_processor.user_service.services;

import com.dkds.payment_processor.user_service.dto.FileDto;
import com.dkds.payment_processor.user_service.dto.UserDto;
import com.dkds.payment_processor.user_service.entities.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User createKyc(User request);

    User getKycById(Long id);

    User updateKyc(Long id, User user);

    void saveDocuments(Long id, List<FileDto> fileList);
}
