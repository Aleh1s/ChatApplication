package com.palamar.chatapplication.controller;

import com.palamar.chatapplication.body.request.UpdateProfileRequest;
import com.palamar.chatapplication.entity.Image;
import com.palamar.chatapplication.entity.user.UserEntity;
import com.palamar.chatapplication.service.UserService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@CrossOrigin("http://localhost:3000")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<Object> getAllUsers() {
        List<UserEntity> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.ACCEPTED);
    }

    @GetMapping("/{username}")
    public ResponseEntity<Object> getUserByUsername(@PathVariable String username) {
        return new ResponseEntity<>(userService.getUserByUsername(username), HttpStatus.ACCEPTED);
    }

    @PostMapping("/profile/image/{username}")
    public ResponseEntity<Object> addProfileImage(@PathVariable String username, @RequestParam("file") MultipartFile file) {
        return new ResponseEntity<>(userService.addProfileImage(username, file), HttpStatus.ACCEPTED);
    }

    @GetMapping("/profile/image/{username}")
    @Transactional
    public ResponseEntity<Object> getProfileImageByUsername(@PathVariable String username) {
        Image profileImage = userService.getProfileImageByUsername(username);
        if (profileImage == null)
            return ResponseEntity.badRequest()
                    .body(null);

        return ResponseEntity.ok()
                .header("fileName", profileImage.getOriginalFileName())
                .contentType(MediaType.valueOf(profileImage.getContentType()))
                .contentLength(profileImage.getSize())
                .body(new InputStreamResource(new ByteArrayInputStream(profileImage.getBytes())));

    }

//    @PutMapping("/profile/image/{username}")
//    public ResponseEntity<Object> updateProfileImage(@PathVariable String username, @RequestParam MultipartFile file) {
//        return new ResponseEntity<>(userService.updateProfileImageByUsername(username, file), HttpStatus.ACCEPTED);
//    }

    @GetMapping("/profile/image/status/{username}")
    public ResponseEntity<Object> profileImageExist(@PathVariable String username) {
        return new ResponseEntity<>(userService.profileImageExists(username), HttpStatus.ACCEPTED);
    }

    @GetMapping("/profile/{username}")
    public ResponseEntity<Object> getUserProfile(@PathVariable String username) {
        return new ResponseEntity<>(userService.getUserProfileDataByUsername(username), HttpStatus.ACCEPTED);
    }

    @PostMapping("/profile/{username}")
    public ResponseEntity<Object> updateProfile(@PathVariable String username, @RequestBody UpdateProfileRequest request) {
        return new ResponseEntity<>(userService.updateProfile(username, request), HttpStatus.ACCEPTED);
    }

}
