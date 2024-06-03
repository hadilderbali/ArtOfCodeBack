package com.artofcode.artofcodebck.Controllers;

import com.artofcode.artofcodebck.Domaine.Response;
import com.artofcode.artofcodebck.Entities.Club;
import com.artofcode.artofcodebck.Entities.Event;
import com.artofcode.artofcodebck.Services.IClubService;
import com.artofcode.artofcodebck.Services.IEventService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("http://localhost:4200")
@AllArgsConstructor
@RequestMapping("/clubs")
public class ClubContoller {
    private IClubService clubService;
    @PostMapping("/createClub/")

    public ResponseEntity<Response> createClub(@RequestParam("file") MultipartFile file,
                                               @ModelAttribute Club club) {
        try {
            // Save the uploaded image file
            String imageFileName = saveImage(file);

            // Update the JobOffer entity with the image file name
            club.setFileName(imageFileName);

            // Save the job offer
            Club savedClub = clubService.createOrUpdateClub(club);

            if (savedClub != null) {
                return new ResponseEntity<>(new Response(""), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new Response("Club not saved"), HttpStatus.BAD_REQUEST);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(new Response("Error adding club"), HttpStatus.INTERNAL_SERVER_ERROR);
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
    @GetMapping("/cphoto/{clubId}")
    public byte[] getClubPhotoById(@PathVariable("clubId") Long clubId) throws Exception {
        return clubService.getClubPhotoById(clubId);
    }

    @PutMapping("/updateClub/{id}")
    public Club updateClub(@PathVariable Long id, @RequestBody Club club) {
        club.setClubId(id);
        return clubService.createOrUpdateClub(club);
    }

    @DeleteMapping("/deleteClub/{id}")
    public void deleteClub(@PathVariable Long id) {
        clubService.deleteClub(id);
    }

    @GetMapping("/get")
    public List<Club> getAllClubs() {
        return clubService.getAllClubs();
    }
    @GetMapping("/getClubById/{id}")
    public ResponseEntity<Club> getClubById(@PathVariable Long id) {
        Optional<Club> eventOptional = clubService.retrieveClubById(id);
        return eventOptional.map(event -> new ResponseEntity<>(event, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

}
