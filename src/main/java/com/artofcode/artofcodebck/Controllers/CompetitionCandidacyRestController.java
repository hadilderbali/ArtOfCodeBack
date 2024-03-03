package com.artofcode.artofcodebck.Controllers;

import com.artofcode.artofcodebck.Entities.CompetitionCandidacy;
import com.artofcode.artofcodebck.Services.CompetitionCandidacy.ICompetitionCandidacyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Candidacy")
@CrossOrigin
public class CompetitionCandidacyRestController {

    @Autowired
    private ICompetitionCandidacyService competitionCandidacyService;

    @GetMapping("/{IdCandidacy}")
    public CompetitionCandidacy getCompetitionCandidacyById(@PathVariable(name = "IdCandidacy") Long id){
        return competitionCandidacyService.getCompetitionCandidacyById(id);
    }

    @GetMapping("/allCandidacies")
    public List<CompetitionCandidacy> getAllCompetitionCandidacies(){
        return competitionCandidacyService.getAllCompetitionCandidacies();
    }

    @PostMapping("/addCandidacy")
    public CompetitionCandidacy addCompetitionCandidacy(@RequestBody CompetitionCandidacy competitionCandidacy){
        return competitionCandidacyService.addCompetitionCandidacy(competitionCandidacy);

    }

    @PutMapping("/updateCandidacy")
    public CompetitionCandidacy updateCompetitionCandidacy(@RequestBody CompetitionCandidacy competitionCandidacy){
        return competitionCandidacyService.updateCompetitionCandidacy(competitionCandidacy);
    }

    @DeleteMapping("/deleteCandidacy/{IdCandidacy}")
    public void deleteCompetitionCandidacy(@PathVariable(name = "IdCandidacy") Long id){
        competitionCandidacyService.deleteCompetitionCandidacy(id);}

}
