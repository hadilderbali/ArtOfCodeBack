package com.artofcode.artofcodebck.Services.CompetitionCandidacy;

import com.artofcode.artofcodebck.Entities.Competition;
import com.artofcode.artofcodebck.Entities.CompetitionCandidacy;

import java.util.List;

public interface ICompetitionCandidacyService {
    CompetitionCandidacy getCompetitionCandidacyById(Long id);

    List<CompetitionCandidacy> getAllCompetitionCandidacies();

    CompetitionCandidacy addCompetitionCandidacy(CompetitionCandidacy competitionCandidacy,Long idcomptetion,Long userId);


    void deleteCompetitionCandidacy(Long id);

    CompetitionCandidacy updateCompetitionCandidacy(CompetitionCandidacy competitionCandidacy, Long id);
}
