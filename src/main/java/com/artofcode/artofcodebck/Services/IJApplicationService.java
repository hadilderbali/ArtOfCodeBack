package com.artofcode.artofcodebck.Services;

import com.artofcode.artofcodebck.Entities.JobApplication;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.IOException;
import java.util.List;

public interface IJApplicationService {
     JobApplication addJobApp(JobApplication jobApplication, Long jobOfferId, Long dancerId);
     void updateJobApplicationWithoutImage(Long id, JobApplication updatedJobApplication) throws IOException;
    byte[] getJobAppPhotoById(Long idDancer) throws Exception;
    void acceptJobApplication(Long id);
    void deleteJobApp(Long idD);
    List<JobApplication> getJobApps();
    JobApplication getJobApp(Long idD);
    Page<JobApplication> getJobApplicationsByDancerId(Long dancerId,int page,int size);
    JobApplication getJobApplicationByJobOfferId(Long jobOfferId) ;
    }
