package com.artofcode.artofcodebck.Repositories;

import com.artofcode.artofcodebck.Entities.CompetitionCandidacy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompetitionCandidacyRepository extends JpaRepository<CompetitionCandidacy,Long> {

}
