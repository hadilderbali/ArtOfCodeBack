package com.artofcode.artofcodebck.Repositories;

import com.artofcode.artofcodebck.Entities.Challenge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChallengeRepository extends JpaRepository<Challenge,Long> {

}
