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
public class CompetitionService {
    private  final ICompetitionRepository competitionRepository;
    public Competition addCompetition(Competition competition){
        return CompetitionRepository.save(competition);
    }
 public  Competition updateCompetition(Competition competition){
        return CompetitionRepository.save(competition);
 }
 public  void deleteCompetition(Long IdCompetition) {
     CompetitionRepository.deleteById(IdCompetition);

 }
    public Competition getCompetition(Long IdCompetition) {
        return  CompetitionRepository.findById(IdCompetition).orElse(null);
    }
    
}
