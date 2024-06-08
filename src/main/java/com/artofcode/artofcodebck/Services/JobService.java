package com.artofcode.artofcodebck.Services;


import com.artofcode.artofcodebck.Entities.JobOffer;
import com.artofcode.artofcodebck.Repositories.IJobOfferRepository;
import com.artofcode.artofcodebck.user.UserRepository;
import com.artofcode.artofcodebck.user.Role;
import jakarta.servlet.ServletContext;
import com.artofcode.artofcodebck.user.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Slf4j
public class JobService implements  IJobService {
    private  final IJobOfferRepository jobOfferRepository;
   private final UserRepository userRepository;
    private  final ServletContext context;

    @Override
    public JobOffer addJobOffer(JobOffer jobOffer, Integer recruiterId, HttpServletRequest request, HttpServletResponse response) {
        // Step 1: Retrieve the User (recruiter) entity by ID
        User recruiter = userRepository.findByIdAndRole(recruiterId, Role.RECRUITER);
        if (recruiter == null) {
            // Handle case when recruiter with given ID does not exist
            return null;
        }

        // Step 2: Set the User entity as the recruiter for the JobOffer
        jobOffer.setUser(recruiter);

        // Step 3: Save the JobOffer entity to persist the association
        JobOffer savedJobOffer = jobOfferRepository.save(jobOffer);



        return savedJobOffer;
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
    public Page<JobOffer> getJobOffers(String keyword, String location, Pageable pageable) {
        // Retrieve all job offers without applying pagination
        List<JobOffer> allJobOffers = jobOfferRepository.findAll();

        // Prioritize job offers that match the provided keyword and location
        List<JobOffer> prioritizedJobOffers = allJobOffers.stream()
                .filter(jobOffer -> matchesSearchCriteria(jobOffer, keyword, location))
                .sorted((job1, job2) -> {
                    boolean match1 = matchesSearchCriteria(job1, keyword, location);
                    boolean match2 = matchesSearchCriteria(job2, keyword, location);

                    if (match1 && !match2) {
                        return -1; // job1 matches, job2 doesn't, so job1 comes first
                    } else if (!match1 && match2) {
                        return 1; // job2 matches, job1 doesn't, so job2 comes first
                    } else {
                        return 0; // Both match or both don't match, maintain the original order
                    }
                })
                .collect(Collectors.toList());

        // Add the remaining job offers that do not match the search criteria
        allJobOffers.removeAll(prioritizedJobOffers);
        prioritizedJobOffers.addAll(allJobOffers);

        // Paginate the prioritized job offers
        int start = Math.min((int) pageable.getOffset(), prioritizedJobOffers.size());
        int end = Math.min((start + pageable.getPageSize()), prioritizedJobOffers.size());

        return new PageImpl<>(prioritizedJobOffers.subList(start, end), pageable, prioritizedJobOffers.size());
    }

    private boolean matchesSearchCriteria(JobOffer jobOffer, String keyword, String location) {
        // Check if job offer matches search criteria
        Predicate<JobOffer> matchesKeyword =
                job -> keyword == null || job.getTitle().toLowerCase().contains(keyword.toLowerCase());
        Predicate<JobOffer> matchesLocation =
                job -> location == null || job.getLocation().equalsIgnoreCase(location);

        return matchesKeyword.or(matchesLocation).test(jobOffer);
    }


    @Override
    public byte[] getJobOfferPhotoById(Long idR) throws Exception {
        JobOffer jobOffer = jobOfferRepository.findById(idR).orElseThrow(() -> new Exception("image Job Offer not found"));
        return Files.readAllBytes(Paths.get(context.getRealPath("/images/") + jobOffer.getFileName()));
    }
    public Page<JobOffer> getJobOffersByUserId(Integer userid, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return jobOfferRepository.findByUserId(userid, pageable);
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
