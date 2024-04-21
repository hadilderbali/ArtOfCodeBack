package com.artofcode.artofcodebck.Services;

import com.artofcode.artofcodebck.Entities.JobOffer;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IJobService {
    JobOffer addJobOffer(JobOffer jobOffer,Long recruiterId);
    void updateJobOfferWithoutFile(Long id, JobOffer updatedJobOffer);
    JobOffer getJobOffer(Long IdR);
    Page<JobOffer> getJobOffersByUserId(Long userid, int page, int size)  ;

    List<JobOffer> getJobOffers();
    byte[] getJobOfferPhotoById(Long idR) throws Exception;
    void removeJobOffer(Long IdR);
    Page<JobOffer> searchJobOffers(String title, String location, int page, int size);
}
