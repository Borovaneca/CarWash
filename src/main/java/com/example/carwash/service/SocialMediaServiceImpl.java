package com.example.carwash.service;

import com.example.carwash.mapper.CustomMapper;
import com.example.carwash.model.dtos.SocialMediaAddDTO;
import com.example.carwash.model.entity.SocialMedia;
import com.example.carwash.model.entity.User;
import com.example.carwash.repository.SocialMediaRepository;
import com.example.carwash.service.interfaces.SocialMediaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SocialMediaServiceImpl implements SocialMediaService {

    private final SocialMediaRepository socialMediaRepository;
    private final CustomMapper customMapper;

    @Autowired
    public SocialMediaServiceImpl(SocialMediaRepository socialMediaRepository, CustomMapper customMapper) {
        this.socialMediaRepository = socialMediaRepository;
        this.customMapper = customMapper;
    }


    @Override
    public SocialMedia addSocialMediaToUser(SocialMediaAddDTO socialMediaAddDTO, User user) {
        SocialMedia socialMedia = customMapper.socialMediaAddDTOToSocialMedia(socialMediaAddDTO, user);
        return socialMediaRepository.save(socialMedia);
    }


    @Override
    public void delete(SocialMedia media) {
        socialMediaRepository.delete(media);
    }

    @Override
    public SocialMedia getByNameAndUser(String socialName, User user) {
       return socialMediaRepository.findByTypeAndUser(socialName, user).orElse(null);
    }
}
