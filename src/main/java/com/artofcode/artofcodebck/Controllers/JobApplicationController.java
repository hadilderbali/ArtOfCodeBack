package com.artofcode.artofcodebck.Controllers;
import com.artofcode.artofcodebck.Entities.JobApplication;
import com.artofcode.artofcodebck.Entities.JobOffer;
import com.artofcode.artofcodebck.Services.EmailService;
import com.artofcode.artofcodebck.Services.IJApplicationService;
import com.artofcode.artofcodebck.domaine.Response;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FileUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/jobapplication")
@CrossOrigin("http://localhost:4200")
public class JobApplicationController {
    final IJApplicationService ijApplicationService ;
    final EmailService emailService;

    @PostMapping("/addJobApp")
    public ResponseEntity<Response>  addJobApp(@RequestParam(value = "file", required = false) MultipartFile file, @ModelAttribute JobApplication jobApplication,@RequestParam("jobOfferId") Long jobOfferId,  @RequestParam("dancerId") Long dancerId){

        try {
            String imageFileName = null;
            if (file != null && !file.isEmpty()) {
                // Save the uploaded image file
                imageFileName = saveImage(file);
                // Update the JobApplication entity with the image file name
                jobApplication.setImageA(imageFileName);
            }

            // Save the job application
            // Assuming you have a service method to save the job application
            JobApplication savedJobApplication = ijApplicationService.addJobApp(jobApplication,jobOfferId,dancerId);
            // Send email notification
            if (savedJobApplication != null) {
                sendEmailNotification(savedJobApplication.getJobOffer().getEmail());

                return new ResponseEntity<>(new Response(""), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new Response("Job application not saved"), HttpStatus.BAD_REQUEST);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(new Response("Error applying for job"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Helper method to save the uploaded image file
    private String saveImage(MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename();

        // Define the directory path relative to the application's root directory
        String directoryPath = System.getProperty("user.dir") + "/src/main/webapp/ImagesJobApp/";

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
        // Send email notification to the provided email address
        private void sendEmailNotification(String recipientEmail) {
            try {
                String subject = "New Job Application Received";
                String content = "A new job application has been received. Please check your account for details.\n\n";

                emailService.sendEmail(recipientEmail, subject, content);
            } catch (Exception e) {
                e.printStackTrace(); // Handle email sending exception
            }
        }
@PutMapping("update/{id}")
public ResponseEntity<String> updateJobApplicationWithoutImage(@PathVariable("id") Long id,
                                                               @RequestBody JobApplication updatedJobApplication) {
    try {
       ijApplicationService.updateJobApplicationWithoutImage(id, updatedJobApplication);
        return ResponseEntity.ok("Job application updated successfully without image.");
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Failed to update job application without image: " + e.getMessage());
    }
}
    @PutMapping("/accept/{id}")
    public ResponseEntity<Void> acceptJobApplication(@PathVariable Long id) {
      ijApplicationService.acceptJobApplication(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/getJobApp/{idJobApp}")
    public JobApplication getJobApp(@PathVariable("idJobApp")Long idD){
        return ijApplicationService.getJobApp(idD);
    }
    @GetMapping("/getJobApps")
    public List<JobApplication> getJobApps(){
        return ijApplicationService.getJobApps();
    }
    @DeleteMapping("/deleteJobApp/{idJobApp}")
    public  void deleteJobApp (@PathVariable("idJobApp") Long idD){
        ijApplicationService.deleteJobApp(idD);
    }
    @GetMapping("/ImgJobApp/{idDancer}")
    public byte[] getJobAppPhotoById(@PathVariable("idDancer")Long idDancer) throws Exception {
        return ijApplicationService.getJobAppPhotoById(idDancer);
    }
    @GetMapping("/getpage/{dancerId}")
    public ResponseEntity<Page<JobApplication>> getJobApplicationsByDancerId(@PathVariable Long dancerId,@RequestParam(defaultValue = "0") int page,
                                                                             @RequestParam(defaultValue = "3") int size) {

        Page<JobApplication> jobOffers = ijApplicationService.getJobApplicationsByDancerId(dancerId, page, size);
        return ResponseEntity.ok(jobOffers);}

    @GetMapping("/{jobOfferId}")
    public JobApplication getJobApplicationByJobOfferId(@PathVariable Long jobOfferId) {
        return ijApplicationService.getJobApplicationByJobOfferId(jobOfferId);
    }
}
