package com.palamar.chatapplication.service;

import com.palamar.chatapplication.body.request.UpdateProfileRequest;
import com.palamar.chatapplication.body.response.UpdateProfileResponse;
import com.palamar.chatapplication.body.response.UserProfileDataResponse;
import com.palamar.chatapplication.entity.Image;
import com.palamar.chatapplication.entity.user.Gender;
import com.palamar.chatapplication.entity.user.UserEntity;
import com.palamar.chatapplication.repository.ImageRepository;
import com.palamar.chatapplication.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final ImageRepository imageRepository;

    @Autowired
    public UserService(UserRepository userRepository, ImageRepository imageRepository) {
        this.userRepository = userRepository;
        this.imageRepository = imageRepository;
    }


    public boolean existsUserWithEmail(String email) {
        return userRepository.existsUserEntitiesByEmail(email);
    }

    public List<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }

    public UserEntity getUserByUsername(String username) {
        return userRepository.findUserByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("user does not exist"));
    }

    @Transactional
    public Image getProfileImageByUsername(String username) {
        UserEntity currentUser = userRepository.findUserEntityByUsernameJoinFetchProfileImage(username)
                .orElse(null);

        if (currentUser == null)
            return null;

        return currentUser.getProfileImage();
    }

    @Transactional
    public Image updateProfileImageByUsername(String username, MultipartFile file) {
        UserEntity currentUser = userRepository.findUserEntityByUsernameJoinFetchProfileImage(username)
                .orElseThrow(() -> new IllegalArgumentException("user does not exist"));
        Image newImage = fileToImage(file);
        Image profileImage = currentUser.getProfileImage();
        currentUser.removeProfileImage(profileImage);
        currentUser.addProfileImage(profileImage);
        return imageRepository.save(newImage);
    }

    @Transactional
    public Image addProfileImage(String username, MultipartFile file) {
        Image profileImage = null;
        UserEntity currentUser = userRepository.findUserByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("user does not exist"));
        if (file.getSize() != 0) {
            profileImage = fileToImage(file);
            currentUser.addProfileImage(profileImage);
            profileImage = imageRepository.save(profileImage);
        }
        return profileImage;
    }

    @Transactional
    public boolean profileImageExists(String username) {
        return imageRepository.existsImageByUserUsername(username);
    }

    private Image fileToImage(MultipartFile file) {
        Image image = null;
        try {
            image = Image.builder()
                    .name(file.getName())
                    .originalFileName(file.getOriginalFilename())
                    .bytes(file.getBytes())
                    .contentType(file.getContentType())
                    .size(file.getSize())
                    .build();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }

    public UserProfileDataResponse getUserProfileDataByUsername(String username) {
        UserEntity currentUser = userRepository.findUserByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("user does not exist"));

        return new UserProfileDataResponse(
                currentUser.getEmail(),
                currentUser.getUsername(),
                currentUser.getDescription(),
                currentUser.getPhoneNumber(),
                currentUser.getGender()
        );
    }


    @Transactional
    public UpdateProfileResponse updateProfile(String username, UpdateProfileRequest request) {
        UserEntity currentUser = userRepository.findUserByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("user does not exist"));
        String description = request.description();
        String phoneNumber = request.phoneNumber();
        Gender gender = request.gender();
        if (description != null && description.length() > 0)
            currentUser.setDescription(request.description());
        if (phoneNumber != null && phoneNumber.length() == 10)
            currentUser.setPhoneNumber(request.phoneNumber());
        if (gender != null)
            currentUser.setGender(request.gender());
        return new UpdateProfileResponse(
                description,
                phoneNumber,
                gender
        );
    }
}
