package com.artofcode.artofcodebck.Repositories;

import com.artofcode.artofcodebck.Entities.Level;
import com.artofcode.artofcodebck.Entities.Tutorial;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ITutorialRepository extends JpaRepository<Tutorial,Long> {
    List<Tutorial> findByLevel(Level level);

    @Query("SELECT t FROM Tutorial t WHERE (:level IS NULL OR t.level = :level) " +
            "AND (:minDuration IS NULL OR t.duration >= :minDuration) " +
            "AND (:maxDuration IS NULL OR t.duration <= :maxDuration)")
    List<Tutorial> findByLevelAndDuration(@Param("level") String level,
                                          @Param("minDuration") Integer minDuration,
                                          @Param("maxDuration") Integer maxDuration);

    Page<Tutorial> findAll(Specification<Tutorial> spec, Pageable pageable);


}

