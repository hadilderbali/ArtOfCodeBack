package com.artofcode.artofcodebck.Repositories;

import com.artofcode.artofcodebck.Entities.Competition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompetitionRepository extends JpaRepository<Competition,Long> {

}
