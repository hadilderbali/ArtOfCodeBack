package com.artofcode.artofcodebck.Repositories;

import com.artofcode.artofcodebck.Entities.Tutorial;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ITutorialRepository extends JpaRepository<Tutorial,Long> {
}
