package com.example.carwash.service.interfaces;

import com.example.carwash.model.entity.ProfileImage;
import com.example.carwash.model.entity.User;
import org.springframework.web.multipart.MultipartFile;

public interface ProfileImageService {
    ProfileImage saveProfileImage(MultipartFile multipartFile, User user);

    ProfileImage getDefaultProfileImage();

    ProfileImage save(ProfileImage profileImage);
}
