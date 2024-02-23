package com.artofcode.artofcodebck.Services;

import com.artofcode.artofcodebck.Entities.Tutorial;

import java.util.List;

public interface ITutorialService {
    Tutorial addTutorial(Tutorial tutorial);
    Tutorial updateTutorial(Tutorial tutorial);
    Tutorial retrieveTutorial (long tutorialId);

    void deleteTutorial (long tutorialId);
    List<Tutorial> retrieveAllTutorial();

}
