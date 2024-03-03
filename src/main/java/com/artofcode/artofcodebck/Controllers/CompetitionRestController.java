package com.artofcode.artofcodebck.Controllers;

import com.artofcode.artofcodebck.Entities.Competition;
import com.artofcode.artofcodebck.Services.Competition.ICompetitionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/competition")
@CrossOrigin
public class CompetitionRestController {

    @Autowired
    private ICompetitionService competitionService;

    @GetMapping("/{IdCompetition}")
    public Competition getCompetitionById(@PathVariable(name = "IdCompetition") Long id){
        return competitionService.getCompetitionById(id);
    }

    @GetMapping("/allCompetitions")
    public List<Competition> getAllCompetitions(){
        return competitionService.getAllCompetitions();
    }

    @PostMapping("/addCompetition")
    public Competition addCompetition(@RequestBody Competition competition){
        return competitionService.addCompetition(competition);

    }

    @PutMapping("/updateCompetition")
    public Competition updateCompetition(@RequestBody Competition competition){
        return competitionService.updateCompetition(competition);
    }

    @DeleteMapping("/deleteCompetition/{IdCompetition}")
    public void deleteCompetition(@PathVariable(name = "IdCompetition") Long id){
        competitionService.deleteCompetition(id);
    }

}
