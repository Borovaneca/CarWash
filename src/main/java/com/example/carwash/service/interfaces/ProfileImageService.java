package com.example.carwash.service.interfaces;

import com.example.carwash.model.entity.ProfileImage;
import org.springframework.web.multipart.MultipartFile;

public interface ProfileImageService {
    ProfileImage saveProfileImage(MultipartFile multipartFile, String username);

    ProfileImage getDefaultProfileImage();

    ProfileImage save(ProfileImage profileImage);
}
