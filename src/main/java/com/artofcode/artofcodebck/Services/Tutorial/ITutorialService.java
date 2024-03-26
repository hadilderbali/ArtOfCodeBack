package com.artofcode.artofcodebck.Services.Tutorial;

import com.artofcode.artofcodebck.Entities.Tutorial;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ITutorialService {
    Tutorial addOrUpdateTutorial(Tutorial tutorial);

    Tutorial getTutorialById (long tutorialId);

    void deleteTutorial (long tutorialId);
    List<Tutorial> getAllTutorials();
    // List<Tutorial> filterCoursesByLevel(Level level);

    Tutorial updateTutorial(Tutorial tutorial,Long id);
   Page<Tutorial> gets(int page, int pageSize);





}
