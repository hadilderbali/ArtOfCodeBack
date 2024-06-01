package com.artofcode.artofcodebck.Services.Grades;
import com.artofcode.artofcodebck.Entities.CompetitionCandidacy;
import com.artofcode.artofcodebck.Entities.Grades;
import com.artofcode.artofcodebck.user.User;
import com.artofcode.artofcodebck.Repositories.CompetitionCandidacyRepository;
import com.artofcode.artofcodebck.Repositories.GradesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class GradesServiceImpl implements IGradesService {

    @Autowired
    private GradesRepository gradesRepository;
    @Autowired
    private CompetitionCandidacyRepository competitionCandidacyRepository;

    @Override
    public Grades getGradesById(Long id) {
        return gradesRepository.findById(id).orElse(null);
    }

    @Override
    public List<Grades> getAllGrades() {
        return  gradesRepository.findAll();
    }

    @Override
    public Grades addGrades(Grades grades,Long idCandidacy) {
        CompetitionCandidacy competitionCandidacy = competitionCandidacyRepository.findById(idCandidacy).get();
        grades.setCompetitionCandidacy(competitionCandidacy);
        return gradesRepository.save(grades);
    }

    @Override
    public Grades updateGrades(Grades grades) {
        return gradesRepository.saveAndFlush(grades);
    }

    @Override
    public void deleteGrades(Long id) {
        if (getGradesById(id)!=null)
        {
            gradesRepository.deleteById(id);
        }
    }
}
