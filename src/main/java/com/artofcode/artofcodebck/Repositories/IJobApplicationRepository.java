package com.artofcode.artofcodebck.Repositories;

import com.artofcode.artofcodebck.Entities.JobApplication;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IJobApplicationRepository extends JpaRepository<JobApplication,Long> {
}
