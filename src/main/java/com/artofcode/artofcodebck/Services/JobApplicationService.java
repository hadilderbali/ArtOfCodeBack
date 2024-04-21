package com.artofcode.artofcodebck.Services;

import com.artofcode.artofcodebck.Entities.JobApplication;
import com.artofcode.artofcodebck.Entities.JobOffer;
import com.artofcode.artofcodebck.Entities.Role;
import com.artofcode.artofcodebck.Entities.User;
import com.artofcode.artofcodebck.Repositories.IJobApplicationRepository;
import com.artofcode.artofcodebck.Repositories.IJobOfferRepository;
import com.artofcode.artofcodebck.Repositories.IUserRepository;
import jakarta.servlet.ServletContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import org.springframework.data.domain.Pageable;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
@Slf4j
@Service
@RequiredArgsConstructor
public class JobApplicationService implements  IJApplicationService {
    private  final IJobApplicationRepository jobApplicationRepository ;
    private  final  IJobOfferRepository jobOfferRepository;
    private  final ServletContext context;
    private final IUserRepository userRepository;

    @Override
    @Transactional
    public JobApplication addJobApp(JobApplication jobApplication, Long jobOfferId, Long dancerId) {
        JobOffer jobOffer = jobOfferRepository.findById(jobOfferId).orElse(null);
        if (jobOffer == null) {
            return null;
        }

        // Step 2: Retrieve the Dancer entity by ID
        User dancer =  userRepository.findByUseridAndRole(dancerId, Role.DANCER);
        if (dancer == null) {
            return null;
        }

        // Step 3: Set the Dancer for the JobApplication
        jobApplication.setUser(dancer);

        // Step 4: Set the JobOffer for the JobApplication
        jobApplication.setJobOffer(jobOffer);

        // Step 5: Save the job application
        return jobApplicationRepository.save(jobApplication);
    }


    @Override
    public void updateJobApplicationWithoutImage(Long id, JobApplication updatedJobApplication)
throws IOException {
            // Vous pouvez implémenter ici la mise à jour de la candidature à l'emploi sans l'image
            JobApplication existingJobApplication = jobApplicationRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Job application not found with ID: " + id));

            // Update attributes
            existingJobApplication.setNameD(updatedJobApplication.getNameD());
            existingJobApplication.setEmailDancer(updatedJobApplication.getEmailDancer());
            existingJobApplication.setPhoneNumberDancer(updatedJobApplication.getPhoneNumberDancer());
            existingJobApplication.setCoverLetter(updatedJobApplication.getCoverLetter());

            // Save the updated job application
            jobApplicationRepository.save(existingJobApplication);
        }



    @Override
    public byte[] getJobAppPhotoById(Long idDancer) throws Exception {
        JobApplication jobApplication = jobApplicationRepository.findById(idDancer)
                .orElseThrow(() -> new Exception("Image not found"));
        String imageFileName = jobApplication.getImageA();

        // Get the real path to the ImagesJobApp directory using getRealPath
        String imagePath = context.getRealPath("/ImagesJobApp/") + imageFileName;

        return Files.readAllBytes(Paths.get(imagePath));
    }


    @Override
    public void acceptJobApplication(Long id) {
        JobApplication jobApplication = jobApplicationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Job application not found with ID: " + id));

        jobApplication.setStatus(true); // Set status to true (accepted)

        // Save the updated job application with the new status
        jobApplicationRepository.save(jobApplication);
    }


    @Override
    public void deleteJobApp(Long idD) {
        jobApplicationRepository.deleteById(idD);
    }



    @Override
    public List<JobApplication> getJobApps() {
        return jobApplicationRepository.findAll();
    }



    @Override
    public JobApplication getJobApp(Long idD) {
        return  jobApplicationRepository.findById(idD).orElse(null);
    }


    @Override
    public Page<JobApplication> getJobApplicationsByDancerId(Long dancerId,int page,int size) {
        Pageable pageable = PageRequest.of(page,size);
        return jobApplicationRepository.findByUserUserid(dancerId,pageable);
    }

    @Override
    public JobApplication getJobApplicationByJobOfferId(Long jobOfferId) {
        return  jobApplicationRepository.findJobApplicationByJobOfferIdR(jobOfferId);
    }
}

