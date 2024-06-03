package com.artofcode.artofcodebck.Repositories;

import com.artofcode.artofcodebck.Entities.ReclamationCompetition;
import com.artofcode.artofcodebck.Entities.Tutorial;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IReclamationCompetitionRepository extends JpaRepository<ReclamationCompetition,Long> {
    Page<ReclamationCompetition> findAll(Specification<Tutorial> spec, Pageable pageable);

    List<ReclamationCompetition> findByType(String type);


}

