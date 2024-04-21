package com.artofcode.artofcodebck.Services;


import com.artofcode.artofcodebck.Entities.JobOffer;
import com.artofcode.artofcodebck.Entities.Role;
import com.artofcode.artofcodebck.Entities.User;
import com.artofcode.artofcodebck.Repositories.IJobOfferRepository;
import com.artofcode.artofcodebck.Repositories.IUserRepository;
import jakarta.servlet.ServletContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class JobService implements  IJobService {
    private  final IJobOfferRepository jobOfferRepository;
   private final IUserRepository userRepository;
    private  final ServletContext context;


    @Override
    public JobOffer addJobOffer(JobOffer jobOffer, Long recruiterId) {
        // Step 1: Retrieve the User (recruiter) entity by ID
        User recruiter = userRepository.findByUseridAndRole(recruiterId, Role.RECRUITER);
        if (recruiter == null) {
            // Handle case when recruiter with given ID does not exist
            return null;
        }

        // Step 2: Set the User entity as the recruiter for the JobOffer
        jobOffer.setUser(recruiter);

        // Step 3: Save the JobOffer entity to persist the association
        return jobOfferRepository.save(jobOffer);
    }


    @Override
    public void updateJobOfferWithoutFile(Long id, JobOffer updatedJobOffer) {
        JobOffer existingJobOffer = jobOfferRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Job offer not found with ID: " + id));

        existingJobOffer.setTitle(updatedJobOffer.getTitle());
        existingJobOffer.setLocation(updatedJobOffer.getLocation());
        existingJobOffer.setNumber(updatedJobOffer.getNumber());
        existingJobOffer.setDatePost(updatedJobOffer.getDatePost());
        existingJobOffer.setDescription(updatedJobOffer.getDescription());
        existingJobOffer.setEmail(updatedJobOffer.getEmail());
        existingJobOffer.setSalaryRange(updatedJobOffer.getSalaryRange());
        existingJobOffer.setJobType(updatedJobOffer.getJobType());

        jobOfferRepository.save(existingJobOffer);
    }






    @Override
    public JobOffer getJobOffer(Long IdR) {
        return jobOfferRepository.findById(IdR).orElse(null);
    }

    @Override
    public List<JobOffer> getJobOffers() {
        return jobOfferRepository.findAll();
    }

    @Override
    public byte[] getJobOfferPhotoById(Long idR) throws Exception {
        JobOffer jobOffer = jobOfferRepository.findById(idR).orElseThrow(() -> new Exception("image Job Offer not found"));
        return Files.readAllBytes(Paths.get(context.getRealPath("/Images/") + jobOffer.getFileName()));
    }
    public Page<JobOffer> getJobOffersByUserId(Long userid, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return jobOfferRepository.findByUserUserid(userid, pageable);
    }

    @Override
    public void removeJobOffer(Long IdR) {

        jobOfferRepository.deleteById(IdR);
    }


    @Override
    public Page<JobOffer> searchJobOffers(String title, String location, int page, int size) {
        log.info("Searching job offers with title '{}', location '{}' for page {} of size {}", title, location, page, size);
        Pageable pageable =  PageRequest.of(page, size);

        if ((title != null && !title.isEmpty()) && (location != null && !location.isEmpty())) {
            return jobOfferRepository.findByTitleContainingAndLocationContaining(title, location, pageable);
        } else if (title != null && !title.isEmpty()) {
            return jobOfferRepository.findByTitleContaining(title, pageable);
        } else if (location != null && !location.isEmpty()) {
            return jobOfferRepository.findByLocationContaining(location, pageable);
        }
        return jobOfferRepository.findAll(pageable);
    }
}
