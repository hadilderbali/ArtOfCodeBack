package com.artofcode.artofcodebck.Services.Challenge;

import com.artofcode.artofcodebck.Entities.Challenge;

import java.util.List;

public interface IChallengeService {
     Challenge getChallengeById(Long id);
     List<Challenge> getAllChallenges();
     Challenge addChallenge(Challenge challenge);
     Challenge updateChallenge(Challenge challenge);
     void deleteChallenge(Long id);

}
