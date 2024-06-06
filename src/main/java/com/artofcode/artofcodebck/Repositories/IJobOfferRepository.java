package com.artofcode.artofcodebck.Repositories;

import com.artofcode.artofcodebck.Entities.JobOffer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IJobOfferRepository extends JpaRepository<JobOffer,Long> {
    Page<JobOffer> findByTitleContainingAndLocationContaining(String title, String location, Pageable pageable);

    Page<JobOffer> findByTitleContaining(String title, Pageable pageable);

    Page<JobOffer> findByLocationContaining(String location, Pageable pageable);


    Page<JobOffer> findByUserId(Integer userId, Pageable pageable);

    Page<JobOffer> findByTitleContainingIgnoreCaseAndLocationContainingIgnoreCase(String keyword, String location, Pageable pageable);

    Page<JobOffer> findByTitleContainingIgnoreCase(String keyword, Pageable pageable);

    Page<JobOffer> findByLocationContainingIgnoreCase(String location, Pageable pageable);
}
