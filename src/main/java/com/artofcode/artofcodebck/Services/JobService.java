package com.artofcode.artofcodebck.Services;


import com.artofcode.artofcodebck.Entities.JobApplication;
import com.artofcode.artofcodebck.Entities.JobOffer;
import com.artofcode.artofcodebck.Repositories.IJobApplicationRepository;
import com.artofcode.artofcodebck.Repositories.IJobOfferRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class JobService {
    private  final IJobOfferRepository jobOfferRepository;


    public JobOffer addJobOffer(JobOffer jobOffer){
        return jobOfferRepository.save(jobOffer);
    }
    public  JobOffer updateJobApplication(JobOffer jobOffer){
        return jobOfferRepository.save(jobOffer);
    }
    public JobOffer getJobOffer(Long IdR){
        return jobOfferRepository.findById(IdR).orElse(null);
    }

    public List<JobOffer> getJobOffers(){
        return jobOfferRepository.findAll();
    }



    public void removeJobOffer(Long IdR){
        jobOfferRepository.deleteById(IdR);
    }

}
