package com.artofcode.artofcodebck.Controllers;

import com.artofcode.artofcodebck.Entities.Challenge;
import com.artofcode.artofcodebck.Services.Challenge.IChallengeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/challenge")
@CrossOrigin
public class ChallengeRestController {

    @Autowired
    private IChallengeService challengeService;

    @GetMapping("/{challengeId}")
    public Challenge getChallengeById(@PathVariable(name = "challengeId") Long id){
        return challengeService.getChallengeById(id);
    }

    @GetMapping("/all")
    public List<Challenge> getAllChallenges(){
        return challengeService.getAllChallenges();
    }

    @PostMapping("/add")
    public Challenge addChallenge(@RequestBody Challenge challenge){
        return challengeService.addChallenge(challenge);

    }

    @PutMapping("/update")
    public Challenge updateChallenge(@RequestBody Challenge challenge){
        return challengeService.updateChallenge(challenge);
    }

    @DeleteMapping("/delete/{challengeId}")
    public void deleteChallenge(@PathVariable(name = "challengeId") Long id){
        challengeService.deleteChallenge(id);
    }

}
