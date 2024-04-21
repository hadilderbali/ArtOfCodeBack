package com.artofcode.artofcodebck.Repositories;

import com.artofcode.artofcodebck.Entities.JobApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;

import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;

public interface IJobApplicationRepository extends JpaRepository<JobApplication,Long> {
    Page<JobApplication> findByNameDContaining(String nameD, Pageable pageable);

    JobApplication findJobApplicationByJobOfferIdR(Long jobOfferId);

    Page<JobApplication> findByUserUserid(Long userid, Pageable pageable);
}
