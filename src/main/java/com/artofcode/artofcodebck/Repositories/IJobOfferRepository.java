package com.artofcode.artofcodebck.Repositories;

import com.artofcode.artofcodebck.Entities.JobOffer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IJobOfferRepository extends JpaRepository<JobOffer,Long> {
}
