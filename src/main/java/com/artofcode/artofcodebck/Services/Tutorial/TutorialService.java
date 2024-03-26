package com.artofcode.artofcodebck.Services.Tutorial;

import com.artofcode.artofcodebck.Entities.Level;
import com.artofcode.artofcodebck.Entities.Tutorial;
import com.artofcode.artofcodebck.Repositories.ITutorialRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class TutorialService implements ITutorialService{
    private final ITutorialRepository tutorialRepository;

    @Override
    public Tutorial addOrUpdateTutorial(Tutorial tutorial) {

        return tutorialRepository.save(tutorial);
    }

    @Override
    public Tutorial getTutorialById(long tutorialId) {
        return tutorialRepository.findById(tutorialId).orElse(null);
    }

    @Override
    public void deleteTutorial(long tutorialId) {
        tutorialRepository.deleteById(tutorialId);

    }

    @Override
    public List<Tutorial> getAllTutorials() {
        return tutorialRepository.findAll();
    }
//filter
    public List<Tutorial> filterCoursesByLevel(Level level) {
        return tutorialRepository.findByLevel(level);
    }


    @Override
    public Tutorial updateTutorial(Tutorial tutorial, Long id) {
        Tutorial existingTutorial = tutorialRepository.findById(id).orElse(null);
        if (existingTutorial != null) {
            existingTutorial.setTitle(tutorial.getTitle());
            existingTutorial.setDescription(tutorial.getDescription());
            existingTutorial.setDuration(tutorial.getDuration());
            existingTutorial.setCategory(tutorial.getCategory());
            String video = tutorial.getVideo();
            if (video.length() > 12) {
                existingTutorial.setVideo(video.substring(12));
            }
            return tutorialRepository.saveAndFlush(existingTutorial);
        }
        return null; // Or handle as needed, like throwing an exception
    }
    public Page<Tutorial> gets(int page, int pageSize)  {
        Pageable pageable = PageRequest.of(page - 1, pageSize);
        return tutorialRepository.findAll(pageable);
    }






}



