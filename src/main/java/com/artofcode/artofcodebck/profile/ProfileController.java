package com.artofcode.artofcodebck.profile;
import com.artofcode.artofcodebck.config.JwtService;
import com.artofcode.artofcodebck.user.User;
import com.artofcode.artofcodebck.user.UserService;
import lombok.RequiredArgsConstructor;


import org.apache.catalina.connector.Response;
import org.apache.commons.io.FileUtils;

import org.aspectj.util.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import org.springframework.web.multipart.MultipartFile;

@RestController
@CrossOrigin("http://localhost:8089/")
@RequestMapping("/api/v1/auth/profile")
@RequiredArgsConstructor
public class ProfileController {
    private final ProfileService profileService;
    private final  UserService userService;
    private final JwtService jwtService;
    private static final Logger logger = LoggerFactory.getLogger(ProfileController.class);
    @GetMapping(path = "/profileimage/{name}")
    public byte[] getProfilePhotoByName(@PathVariable("name") String name) throws Exception {
        return profileService.getProfilePhotoByName(name);
    }
    @GetMapping(path = "/profileimageid/{id}")
    public byte[] getProfilePhotoById(@PathVariable("id") Integer id) throws Exception {
        return profileService.getProfilePhotoById(id);
    }
    @PostMapping("/add")
    public ResponseEntity<Void> addProfile(@RequestParam("file") MultipartFile file,
                                           @ModelAttribute profile profile,
                                           @RequestHeader("Authorization") String token) {
        try {
            // Save the uploaded image file
            String imageFileName = saveImage(file);

            // Extract username from the token
            String userEmail = jwtService.extractUsername1(token.substring(7));

            // Retrieve the user from the database using the extracted username
            User user = userService.getUserByEmail(userEmail);

            // Check if the user is authenticated
            if (user == null) {
                // If the user is not found, return UNAUTHORIZED response
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }

            // Associate the profile with the retrieved user
            profile.setUser(user);

            // Update the Profile entity with the image file name
            profile.setProfilephoto(imageFileName);
            profile.getUser().getEmail();
            // Save the profile
            profileService.addProfile(profile);

            // Return OK response
            return ResponseEntity.ok().build();
        } catch (IOException e) {
            e.printStackTrace();
            // Return INTERNAL_SERVER_ERROR response if an error occurs during file processing
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Helper method to save the uploaded image file
    private String saveImage(MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename();

        // Define the directory path relative to the application's root directory
        String directoryPath = System.getProperty("user.dir") + "/src/main/webapp/images/";

        // Create the directory if it doesn't exist
        File directory = new File(directoryPath);
        if (!directory.exists()) {
            boolean created = directory.mkdirs(); // Creates directories if they do not exist
            if (!created) {
                // Log or handle the error if directory creation fails
                throw new IOException("Failed to create directory: " + directoryPath);
            }
        }

        File serverFile = new File(directoryPath + originalFilename);
        FileUtils.writeByteArrayToFile(serverFile, file.getBytes());

        return originalFilename; // Return the original filename
    }
}
