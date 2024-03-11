package com.artofcode.artofcodebck.Services;

import com.artofcode.artofcodebck.Entities.Tutorial;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ITutorialService {
    Tutorial addOrUpdateTutorial(Tutorial tutorial);

    Tutorial findTutorialById (long tutorialId);

    void deleteTutorial (long tutorialId);
    List<Tutorial> findAllTutorials();


}
