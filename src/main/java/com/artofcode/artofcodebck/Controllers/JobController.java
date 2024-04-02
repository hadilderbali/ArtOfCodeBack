package com.artofcode.artofcodebck.Controllers;

import com.artofcode.artofcodebck.Entities.JobOffer;
import com.artofcode.artofcodebck.Services.JobService;
import com.artofcode.artofcodebck.domaine.Response;

import lombok.RequiredArgsConstructor;

import org.apache.commons.io.FileUtils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/job")
@CrossOrigin("http://localhost:4200")
public class JobController {
    final JobService jobService;

    @PostMapping("/addOffer")
    public ResponseEntity<Response> addJobOffer(@RequestParam("file") MultipartFile file,
                                                @ModelAttribute JobOffer jobOffer) {
        try {
            // Save the uploaded image file
            String imageFileName = saveImage(file);

            // Update the JobOffer entity with the image file name
            jobOffer.setFileName(imageFileName);

            // Save the job offer
            JobOffer savedJobOffer = jobService.addJobOffer(jobOffer);

            if (savedJobOffer != null) {
                return new ResponseEntity<>(new Response(""), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new Response("Job offer not saved"), HttpStatus.BAD_REQUEST);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(new Response("Error adding job offer"), HttpStatus.INTERNAL_SERVER_ERROR);
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

    @GetMapping("/offer/{IdR}")
    public  JobOffer getjobofferffer(@PathVariable Long IdR){
        return jobService.getJobOffer(IdR);
}
    @GetMapping("/getoffer")
    public  List<JobOffer> getOffer(){
        return jobService.getJobOffers();
    }
    @GetMapping(path = "/ImgJobOffer/{idR}")
    public byte[] getJobOfferPhoto(@PathVariable("idR") Long idR) throws Exception {
        return jobService.getJobOfferPhotoById(idR);
    }


    @PutMapping("/updatejoboffer/{idJobOffer}")
    public ResponseEntity<Response> updateJobOffer(@PathVariable("idJobOffer") Long idJobOffer,
                                                   @RequestPart("jobOffer") JobOffer updatedJobOffer,
                                                   @RequestParam(value = "file", required = false) MultipartFile file) {
        try {
            // Update the job offer including the image if provided
            JobOffer updatedOffer = jobService.updateJobOffer(idJobOffer, updatedJobOffer, file);
            if (updatedOffer != null) {
                return new ResponseEntity<>(new Response("Job offer updated successfully"), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new Response("Failed to update job offer"), HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new Response("Error updating job offer: " + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/joboffers/search")

    public List<JobOffer> searchJobOffers(
            @RequestParam(name = "keyword", required = false, defaultValue = "") String keyword,
            @RequestParam(name = "location", required = false, defaultValue = "") String location) {
        if (!keyword.isEmpty() && location.isEmpty()) {
            return jobService.searchJobOffersByKeyword(keyword);
        }
        // If only location is provided
        else if (keyword.isEmpty() && !location.isEmpty()) {
            return jobService.searchJobOffersByLocation(location);
        }
        // If both keyword and location are provided
        else {
            return jobService.searchJobOffersByKeywordAndLocation(keyword, location);
        }    }
    @DeleteMapping("/DeletejobOffer/{IdR}")
    public void removeJobOffer(@PathVariable("IdR") Long IdR) {
        jobService.removeJobOffer(IdR);
    }

}
