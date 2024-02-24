package com.artofcode.artofcodebck.Services;


import com.artofcode.artofcodebck.Entities.JobApplication;
import com.artofcode.artofcodebck.Repositories.IJobApplicationRepository;
import com.artofcode.artofcodebck.Repositories.IJobOfferRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
@Service
public class CompetitionCandidacyService {
    private  final ICompetitionRepository competitionRepository;
    public CompetitionCandidacy addCompetitionCandidacy (CompetitionCandidacy git competitionCandidacy){
        return CompetitionCandidacyRepository.save(competitionCandidacy);
    }
 public  CompetitionCandidacy updateCompetitionCandidacy(CompetitionCandidacy competitionCandidacy){
        return CompetitionCandidacyRepository.save(competitionCandidacy);
 }
 public  void deleteCompetitionCandidacy(Long idCandidacy) {
     CompetitionCandidacyRepository.deleteById(idCandidacy);

 }
    public CompetitionCandidacy getCompetitionCandidacy(Long idCandidacy) {
        return  CompetitionCandidacyRepository.findById(idCandidacy).orElse(null);
    }
    
}
