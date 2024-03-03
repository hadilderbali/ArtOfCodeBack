package com.artofcode.artofcodebck.Services.CompetitionCandidacy;


import com.artofcode.artofcodebck.Entities.CompetitionCandidacy;
import com.artofcode.artofcodebck.Repositories.CompetitionCandidacyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CompetitionCandidacyServiceImpl implements ICompetitionCandidacyService {

    @Autowired
    private CompetitionCandidacyRepository competitionCandidacyRepository;

    @Override
    public CompetitionCandidacy getCompetitionCandidacyById(Long id) {
        return competitionCandidacyRepository.findById(id).orElse(null);
    }

    @Override
    public List<CompetitionCandidacy> getAllCompetitionCandidacies() {
        return  competitionCandidacyRepository.findAll();
    }

    @Override
    public CompetitionCandidacy addCompetitionCandidacy(CompetitionCandidacy competitionCandidacy) {
        return competitionCandidacyRepository.saveAndFlush(competitionCandidacy);
    }

    @Override
    public CompetitionCandidacy updateCompetitionCandidacy(CompetitionCandidacy competitionCandidacy) {
        return competitionCandidacyRepository.saveAndFlush(competitionCandidacy);
    }

    @Override
    public void deleteCompetitionCandidacy(Long id) {
        if (getCompetitionCandidacyById(id)!=null)
        {
            competitionCandidacyRepository.deleteById(id);
        }
    }
}
