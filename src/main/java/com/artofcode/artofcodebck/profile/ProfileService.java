package com.artofcode.artofcodebck.profile;


import com.artofcode.artofcodebck.user.User;
import com.artofcode.artofcodebck.user.UserRepository;
import jakarta.servlet.ServletContext;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ProfileService {
    private final ProfileRepository profileRepository;
    private final UserRepository userRepository;

    public profile addProfile(profile profile){
      return profileRepository.save(profile);
  }
    public byte[] getProfilePhotoByName(String photoName) throws Exception {
        String directoryPath = System.getProperty("user.dir") + "/src/main/webapp/images/";
        String imagePath = directoryPath + photoName;
        return Files.readAllBytes(Paths.get(imagePath));
    }
    public byte[] getProfilePhotoById(Integer id) throws Exception {
        Optional<User> user=userRepository.findById(id);
     profile profile=user.get().getProfile();
     String photoName=profile.getProfilephoto();
        String directoryPath = System.getProperty("user.dir") + "/src/main/webapp/images/";
        String imagePath = directoryPath + photoName;
        return Files.readAllBytes(Paths.get(imagePath));
    }

}
