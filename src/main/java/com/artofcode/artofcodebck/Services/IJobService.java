package com.artofcode.artofcodebck.Services;

import com.artofcode.artofcodebck.Entities.JobOffer;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IJobService {
    JobOffer addJobOffer(JobOffer jobOffer, Integer recruiterId, HttpServletRequest request, HttpServletResponse response) ;
    void updateJobOfferWithoutFile(Long id, JobOffer updatedJobOffer);
    JobOffer getJobOffer(Long IdR);
    Page<JobOffer> getJobOffersByUserId(Integer userid, int page, int size)  ;

    Page<JobOffer> getJobOffers(String keyword, String location, Pageable pageable);
    byte[] getJobOfferPhotoById(Long idR) throws Exception;
    void removeJobOffer(Long IdR);
    Page<JobOffer> searchJobOffers(String title, String location, int page, int size);

    }
