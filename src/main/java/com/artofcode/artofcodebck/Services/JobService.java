package com.artofcode.artofcodebck.Services;


import com.artofcode.artofcodebck.Entities.JobApplication;
import com.artofcode.artofcodebck.Entities.JobOffer;
import com.artofcode.artofcodebck.Repositories.IJobApplicationRepository;
import com.artofcode.artofcodebck.Repositories.IJobOfferRepository;
import jakarta.servlet.ServletContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class JobService {
    private  final IJobOfferRepository jobOfferRepository;
    private  final ServletContext context;

    public JobOffer addJobOffer(JobOffer jobOffer){
        return jobOfferRepository.save(jobOffer);
    }
    public JobOffer updateJobOffer(Long id, JobOffer updatedJobOffer, MultipartFile file) {
        Optional<JobOffer> optionalJobOffer = jobOfferRepository.findById(id);
        if (optionalJobOffer.isPresent()) {
            JobOffer existingJobOffer = optionalJobOffer.get();
            // Update job offer details
            existingJobOffer.setTitle(updatedJobOffer.getTitle());
            existingJobOffer.setDescription(updatedJobOffer.getDescription());
            existingJobOffer.setEmail(updatedJobOffer.getEmail());
            existingJobOffer.setLocation(updatedJobOffer.getLocation());
            existingJobOffer.setDatePost(updatedJobOffer.getDatePost());
            existingJobOffer.setNumber(updatedJobOffer.getNumber());
            existingJobOffer.setSalaryRange(updatedJobOffer.getSalaryRange());

            // Check if a new image file is provided
            if (file != null && !file.isEmpty()) {
                try {
                    String fileName = StringUtils.cleanPath(file.getOriginalFilename());
                    // Ensure that the directory to save the file exists
                    File directory = new File("/Images/");
                    if (!directory.exists()) {
                        directory.mkdirs(); // Creates directories if they do not exist
                    }
                    // Save the image file to the specified directory
                    String imagePath = "/Images/" + File.separator + fileName;
                    Files.copy(file.getInputStream(), Paths.get(imagePath), StandardCopyOption.REPLACE_EXISTING);

                    // Set the image filename in the updated job offer
                    existingJobOffer.setFileName(fileName);
                } catch (IOException e) {
                    // Handle file IO exception
                    e.printStackTrace();
                    // You might want to throw an exception or return an error response here
                    throw new RuntimeException("Failed to upload file: " + e.getMessage());
                }
            }

            // Save the updated job offer in the database
            return jobOfferRepository.save(existingJobOffer);
        } else {
            // Return null if the job offer with the given ID does not exist
            return null;
    }}
        public JobOffer getJobOffer(Long IdR){
        return jobOfferRepository.findById(IdR).orElse(null);
    }

    public List<JobOffer> getJobOffers(){
        return jobOfferRepository.findAll();
    }

    public byte[] getJobOfferPhotoById(Long idR) throws Exception {
        JobOffer jobOffer = jobOfferRepository.findById(idR).orElseThrow(() -> new Exception("image Job Offer not found"));
        return Files.readAllBytes(Paths.get(context.getRealPath("/Images/") + jobOffer.getFileName()));
    }


    public List<JobOffer> searchJobOffersByKeyword(String keyword) {
        // Perform search based on keyword
        return jobOfferRepository.findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCase(keyword, keyword);
    }

    public List<JobOffer> searchJobOffersByLocation(String location) {
        // Perform search based on location
        return jobOfferRepository.findByLocationContainingIgnoreCase(location);
    }

    public List<JobOffer> searchJobOffersByKeywordAndLocation(String keyword, String location) {
        // Perform search based on both keyword and location
        return jobOfferRepository.findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCaseAndLocationContainingIgnoreCase(keyword, keyword, location);
    }
    public void removeJobOffer(Long IdR){
        jobOfferRepository.deleteById(IdR);
    }

}
