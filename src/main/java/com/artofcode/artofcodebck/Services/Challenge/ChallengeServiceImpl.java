package com.artofcode.artofcodebck.Services.Challenge;

import com.artofcode.artofcodebck.Entities.Challenge;
import com.artofcode.artofcodebck.Repositories.ChallengeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ChallengeServiceImpl implements IChallengeService {

    @Autowired
    private ChallengeRepository challengeRepository;

    @Override
    public Challenge getChallengeById(Long id) {
        return challengeRepository.findById(id).orElse(null);
    }

    @Override
    public List<Challenge> getAllChallenges() {
        return  challengeRepository.findAll();
    }

    @Override
    public Challenge addChallenge(Challenge challenge) {
        return challengeRepository.saveAndFlush(challenge);
    }

    @Override
    public Challenge updateChallenge(Challenge challenge) {
        return challengeRepository.saveAndFlush(challenge);
    }

    @Override
    public void deleteChallenge(Long id) {
      if (getChallengeById(id)!=null)
      {
          challengeRepository.deleteById(id);
      }
    }
}
