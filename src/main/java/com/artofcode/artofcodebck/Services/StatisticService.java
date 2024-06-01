package com.artofcode.artofcodebck.Services;

import com.artofcode.artofcodebck.user.User;
import com.artofcode.artofcodebck.Repositories.CompetitionCandidacyRepository;
import com.artofcode.artofcodebck.Repositories.CompetitionRepository;
import com.artofcode.artofcodebck.Repositories.GradesRepository;
import com.artofcode.artofcodebck.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.artofcode.artofcodebck.Entities.Grades;

@Service
public class StatisticService {

    @Autowired
    private CompetitionRepository competitionRepository;

    @Autowired
    private CompetitionCandidacyRepository candidacyRepository;

    @Autowired
    private GradesRepository gradesRepository;

    @Autowired
    private UserRepository userRepository;

    public long getTotalCompetitions() {
        return competitionRepository.count();
    }

    public long getTotalCandidacies() {
        return candidacyRepository.count();
    }

    public double getAverageGrade() {
        return gradesRepository.findAll().stream()
                .mapToDouble(Grades::getCandidacyGrade)
                .average().orElse(0);
    }


    public User getUserInfo(int userId) {
        return userRepository.findById(userId).orElse(null);
    }
}
