package com.artofcode.artofcodebck.Repositories;

import com.artofcode.artofcodebck.Entities.Grades;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GradesRepository extends JpaRepository<Grades,Long> {

}
