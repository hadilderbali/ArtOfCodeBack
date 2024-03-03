package com.artofcode.artofcodebck.Services.Competition;

import com.artofcode.artofcodebck.Entities.Competition;
import com.artofcode.artofcodebck.Repositories.CompetitionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CompetitionServiceImpl implements ICompetitionService {

    @Autowired
    private CompetitionRepository competitionRepository;

    @Override
    public Competition getCompetitionById(Long id) {
        return competitionRepository.findById(id).orElse(null);
    }

    @Override
    public List<Competition> getAllCompetitions() {
        return  competitionRepository.findAll();
    }

    @Override
    public Competition addCompetition(Competition competition) {
        return competitionRepository.saveAndFlush(competition);
    }

    @Override
    public Competition updateCompetition(Competition competition) {
        return competitionRepository.saveAndFlush(competition);
    }

    @Override
    public void deleteCompetition(Long id) {
        if (getCompetitionById(id)!=null)
        {
            competitionRepository.deleteById(id);
        }
    }
}
