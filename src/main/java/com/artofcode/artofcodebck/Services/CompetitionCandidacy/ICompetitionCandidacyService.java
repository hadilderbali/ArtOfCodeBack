package com.artofcode.artofcodebck.Services.CompetitionCandidacy;

import com.artofcode.artofcodebck.Entities.CompetitionCandidacy;

import java.util.List;

public interface ICompetitionCandidacyService {
    CompetitionCandidacy getCompetitionCandidacyById(Long id);

    List<CompetitionCandidacy> getAllCompetitionCandidacies();

    CompetitionCandidacy addCompetitionCandidacy(CompetitionCandidacy competitionCandidacy);

    CompetitionCandidacy updateCompetitionCandidacy(CompetitionCandidacy competitionCandidacy);

    void deleteCompetitionCandidacy(Long id);
}
