package com.artofcode.artofcodebck.Repositories;

import com.artofcode.artofcodebck.Entities.JobOffer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.awt.print.Pageable;
import java.util.List;

public interface IJobOfferRepository extends JpaRepository<JobOffer,Long> {

    List<JobOffer> findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCaseAndLocationContainingIgnoreCase(String keyword, String keyword1, String location);

    List<JobOffer> findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCase(String keyword, String keyword1);

    List<JobOffer> findByLocationContainingIgnoreCase(String location);
}
