package com.artofcode.artofcodebck.Services;

import com.artofcode.artofcodebck.Entities.Tutorial;
import com.artofcode.artofcodebck.Repositories.ITutorialRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TutorialService implements ITutorialService{
    private final ITutorialRepository tutorialRepository;

    @Override
    public Tutorial addTutorial(Tutorial tutorial) {
        return tutorialRepository.save(tutorial);
    }

    @Override
    public Tutorial updateTutorial(Tutorial tutorial) {
        return tutorialRepository.save(tutorial);
    }

    @Override
    public Tutorial retrieveTutorial(long tutorialId) {
        return tutorialRepository.findById(tutorialId).orElse(null);
    }

    @Override
    public void deleteTutorial(long tutorialId) {
       tutorialRepository.deleteById(tutorialId);
    }

    @Override
    public List<Tutorial> retrieveAllTutorial() {
        return tutorialRepository.findAll();
    }
}

