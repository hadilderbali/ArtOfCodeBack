package com.artofcode.artofcodebck.Services;


import com.artofcode.artofcodebck.Entities.JobApplication;
import com.artofcode.artofcodebck.Repositories.IJobApplicationRepository;
import com.artofcode.artofcodebck.Repositories.IJobOfferRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
@Service
public class JobService {
    private  final IJobOfferRepository jobOfferRepository;
    private final IJobApplicationRepository jobApplicationRepository;
    public JobApplication addJobApplication(JobApplication jobApplication){
        return jobApplicationRepository.save(jobApplication);
    }
 public  JobApplication updateJobApplication(JobApplication jobApplication){
        return jobApplicationRepository.save(jobApplication);
 }
 public  void deleteJobApplication(Long IdDancer) {
     jobApplicationRepository.deleteById(IdDancer);

 }
    public JobApplication getApplication(Long IdDancer) {
        return  jobApplicationRepository.findById(IdDancer).orElse(null);
    }
    
}
