package com.example.carwash.service.interfaces;

import com.example.carwash.model.dtos.SocialMediaAddDTO;
import com.example.carwash.model.entity.SocialMedia;
import com.example.carwash.model.entity.User;

public interface SocialMediaService {
    SocialMedia addSocialMediaToUser(SocialMediaAddDTO socialMediaAddDTO, User user);

    void delete(SocialMedia media);

    SocialMedia getByNameAndUser(String socialName, User user);
}
