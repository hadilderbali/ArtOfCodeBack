package com.artofcode.artofcodebck.Services.Competition;

import com.artofcode.artofcodebck.Entities.Competition;

import java.util.List;

public interface ICompetitionService {
    Competition getCompetitionById(Long id);

    List<Competition> getAllCompetitions();

    Competition addCompetition(Competition competition);

    Competition updateCompetition(Competition competition);

    void deleteCompetition(Long id);
}
