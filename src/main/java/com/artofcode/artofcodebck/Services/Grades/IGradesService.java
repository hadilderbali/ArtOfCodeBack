package com.artofcode.artofcodebck.Services.Grades;

import com.artofcode.artofcodebck.Entities.Challenge;
import com.artofcode.artofcodebck.Entities.Grades;

import java.util.List;

public interface IGradesService {
    Grades addGrades(Grades grades);

    Grades updateGrades(Grades grades);

    Grades getGradesById(Long id);

    List<Grades> getAllGrades();



    void deleteGrades(Long id);
}
