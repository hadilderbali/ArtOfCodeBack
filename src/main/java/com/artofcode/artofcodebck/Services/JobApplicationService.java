package com.artofcode.artofcodebck.Services;

import com.artofcode.artofcodebck.Entities.JobApplication;
import com.artofcode.artofcodebck.Entities.JobOffer;
import com.artofcode.artofcodebck.Repositories.IJobApplicationRepository;
import com.artofcode.artofcodebck.Repositories.IJobOfferRepository;
import jakarta.servlet.ServletContext;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.naming.Context;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class JobApplicationService {
    private  final IJobApplicationRepository jobApplicationRepository ;
    private  final  IJobOfferRepository jobOfferRepository;
    private  final ServletContext context;
    public JobApplication addJobApp(JobApplication jobApplication, Long jobOfferId) {
        JobOffer jobOffer = jobOfferRepository.findById(jobOfferId).orElse(null);
        // Set the JobOffer for the JobApplication
        jobApplication.setJobOffer(jobOffer);
        // Save the job application
        return jobApplicationRepository.save(jobApplication);
    }

    public void updateJobApplication(Long id, JobApplication updatedJobApplication, MultipartFile image) throws IOException {
        JobApplication existingJobApplication = jobApplicationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Job application not found with ID: " + id));

        // Update attributes
        existingJobApplication.setNameD(updatedJobApplication.getNameD());
        existingJobApplication.setEmailDancer(updatedJobApplication.getEmailDancer());
        existingJobApplication.setPhoneNumberDancer(updatedJobApplication.getPhoneNumberDancer());
        existingJobApplication.setCoverLetter(updatedJobApplication.getCoverLetter());

        // Check if a new image is provided
        if (image != null && !image.isEmpty()) {
            // Save the new image file to the desired directory
            String UPLOAD_DIR = "C:/Users/HADIL/IdeaProjects/ArtOfCodeBack/src/main/webapp/ImagesJobApp/";
            Path filePath = Paths.get(UPLOAD_DIR + "/" + image.getOriginalFilename());
            Files.write(filePath, image.getBytes());

            // Update the job application with the new image file path
            existingJobApplication.setImageA(filePath.toString());
        }

        // Save the updated job application
        jobApplicationRepository.save(existingJobApplication);
    }

    public byte[] getJobAppPhotoById(Long idDancer) throws Exception {
        JobApplication jobApplication = jobApplicationRepository.findById(idDancer)
                .orElseThrow(() -> new Exception("Image not found"));
        String imageFileName = jobApplication.getImageA();

        // Get the real path to the ImagesJobApp directory using getRealPath
        String imagePath = context.getRealPath("/ImagesJobApp/") + imageFileName;

        return Files.readAllBytes(Paths.get(imagePath));
    }

 public  void deleteJobApp (Long idD){
        jobApplicationRepository.deleteById(idD);
 }

    public List<JobApplication> getJobApps(){
        return jobApplicationRepository.findAll();
    }

    public JobApplication getJobApp(Long idD){
        return  jobApplicationRepository.findById(idD).orElse(null);
 }
}
