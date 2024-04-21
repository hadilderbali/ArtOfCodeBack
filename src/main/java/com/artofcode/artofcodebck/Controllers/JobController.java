package com.artofcode.artofcodebck.Controllers;

import com.artofcode.artofcodebck.Entities.JobOffer;
import com.artofcode.artofcodebck.Services.IJobService;
import com.artofcode.artofcodebck.domaine.Response;

import lombok.RequiredArgsConstructor;


import org.apache.commons.io.FileUtils;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/job")
@CrossOrigin("http://localhost:4200")
public class JobController {
    final IJobService iJobService;


    @PostMapping("/addOffer")
    public ResponseEntity<Response> addJobOffer(@RequestParam(value = "file" ) MultipartFile file,
                                                @ModelAttribute JobOffer jobOffer,
                                                @RequestParam("recruiterId") Long recruiterId) {
        try {
            // Check if a file is provided
            String imageFileName = null;
            if (file != null) {
                // Save the uploaded image file
                imageFileName = saveImage(file);
                // Update the JobOffer entity with the image file name
                jobOffer.setFileName(imageFileName);
            }

            // Save the job offer
            JobOffer savedJobOffer = iJobService.addJobOffer(jobOffer, recruiterId);
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
        return iJobService.getJobOffer(IdR);
}
    @GetMapping("/getoffer")
    public  List<JobOffer> getOffer(){
        return iJobService.getJobOffers();
    }
    @GetMapping(path = "/ImgJobOffer/{idR}")
    public byte[] getJobOfferPhoto(@PathVariable("idR") Long idR) throws Exception {
        return iJobService.getJobOfferPhotoById(idR);
    }


    @PutMapping("/update/{id}")
    public ResponseEntity<Response> updateJobOfferWithoutFile(@PathVariable("id") Long id, @RequestBody JobOffer updatedJobOffer) {
        try {
           iJobService.updateJobOfferWithoutFile(id, updatedJobOffer);
            return ResponseEntity.ok(new Response("Job offer updated successfully without modifying the file."));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Response("Failed to update job offer without modifying the file: " + e.getMessage()));
        }
    }


   @GetMapping("/search")
   public Page<JobOffer> searchJobOffers(
           @RequestParam(required = false) String title,
           @RequestParam(required = false) String location,
           @RequestParam(defaultValue = "0") int page,
           @RequestParam(defaultValue = "3") int size) {

       return iJobService.searchJobOffers(title, location, page, size);
   }
    @GetMapping("/Page_joboffers/{userId}")
    public ResponseEntity<Page<JobOffer>> getJobOffersByUserId(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size) {

        Page<JobOffer> jobOffers = iJobService.getJobOffersByUserId(userId, page, size);
        return ResponseEntity.ok(jobOffers);
    }
    @DeleteMapping("/DeletejobOffer/{IdR}")
    public void removeJobOffer(@PathVariable("IdR") Long IdR) {
        iJobService.removeJobOffer(IdR);
    }

}
