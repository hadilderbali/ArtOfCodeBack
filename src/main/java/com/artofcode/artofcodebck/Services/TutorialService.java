package com.artofcode.artofcodebck.Services;

import com.artofcode.artofcodebck.Entities.Tutorial;
import com.artofcode.artofcodebck.Repositories.ITutorialRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TutorialService implements ITutorialService{
    private final ITutorialRepository tutorialRepository;

    @Override
    public Tutorial addOrUpdateTutorial(Tutorial tutorial) {
        String Video = tutorial.getVideo();

        tutorial.setVideo(Video.substring(12));
        return tutorialRepository.save(tutorial);
    }


    @Override
    public Tutorial findTutorialById(long tutorialId) {
        return tutorialRepository.findById(tutorialId).orElse(null);
    }

    @Override
    public void deleteTutorial(long tutorialId) {
       tutorialRepository.deleteById(tutorialId);
    }

    @Override
    public List<Tutorial> findAllTutorials() {
        return tutorialRepository.findAll();
    }





}


