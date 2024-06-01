package com.artofcode.artofcodebck.Controllers;

import com.artofcode.artofcodebck.Entities.Competition;
import com.artofcode.artofcodebck.Entities.CompetitionCandidacy;
import com.artofcode.artofcodebck.Services.CompetitionCandidacy.CompetitionCandidacyServiceImpl;
import com.artofcode.artofcodebck.Services.CompetitionCandidacy.ICompetitionCandidacyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/Candidacy")
@CrossOrigin("http://localhost:4200")
public class CompetitionCandidacyRestController {

    @Autowired
    private ICompetitionCandidacyService competitionCandidacyService;
@Autowired
    CompetitionCandidacyServiceImpl competitionCandidacyServiceimpl;
    @GetMapping("/{IdCandidacy}")
    public CompetitionCandidacy getCompetitionCandidacyById(@PathVariable(name = "IdCandidacy") Long id){
        return competitionCandidacyService.getCompetitionCandidacyById(id);
    }
    @GetMapping("getbyuser/{Iduser}")
    public List<CompetitionCandidacy> getCompetitionCandidacyByIduser(@PathVariable("Iduser") int Iduser){

        return competitionCandidacyServiceimpl.getCompetitionCandidacyByIduser(Iduser);
    }
    @GetMapping("/allCandidacies")
    public List<CompetitionCandidacy> getAllCompetitionCandidacies(){
        return competitionCandidacyService.getAllCompetitionCandidacies();
    }

    @PostMapping("/addCandidacy/{idcomptetion}/{userId}")
    public CompetitionCandidacy addCompetitionCandidacy(@RequestBody CompetitionCandidacy competitionCandidacy,@PathVariable("idcomptetion") Long idcomptetion,@PathVariable("userId") Long userId){
        String video = competitionCandidacy.getVideo();

        competitionCandidacy.setVideo(video.substring(12));
        return competitionCandidacyService.addCompetitionCandidacy(competitionCandidacy,idcomptetion,userId);
    }


    @PutMapping("/updateCompetition/{id}")
    public Competition updateCompetitionCandidacy(@RequestBody CompetitionCandidacy competitionCandidacy, @PathVariable("id") Long id) throws IOException {
        // Vérifier si la vidéo n'est pas null avant de l'extraire
        if (competitionCandidacy.getVideo() != null) {
            // Extraire le chemin de la vidéo à partir de la chaîne complète
            competitionCandidacy.setVideo(competitionCandidacy.getVideo());
        }
        return competitionCandidacyService.updateCompetitionCandidacy(competitionCandidacy, id).getCompetition();
    }

    @DeleteMapping("/deleteCandidacy/{IdCandidacy}")
    public void deleteCompetitionCandidacy(@PathVariable(name = "IdCandidacy") Long id){
        competitionCandidacyService.deleteCompetitionCandidacy(id);}

}
