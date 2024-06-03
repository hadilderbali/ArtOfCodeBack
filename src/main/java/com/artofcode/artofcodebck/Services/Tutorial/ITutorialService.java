package com.artofcode.artofcodebck.Services.Tutorial;

import com.artofcode.artofcodebck.Entities.Comment;
import com.artofcode.artofcodebck.Entities.Level;
import com.artofcode.artofcodebck.Entities.Tutorial;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Map;

public interface ITutorialService {
    Tutorial addOrUpdateTutorial(Tutorial tutorial);

    Tutorial getTutorialById (long tutorialId);

    void deleteTutorial (long tutorialId);
    List<Tutorial> getAllTutorials();


    Tutorial updateTutorial(Tutorial tutorial,Long id);
   Page<Tutorial> gets(int page, int pageSize);

    void likeTutorial(Long tutorialId);
    void dislikeTutorial(Long tutorialId);

    boolean addComment(Long tutorialId, Long userId, Comment comment);
    public List<Comment> getComments(Long tutorialId);
    //filter
    Page<Tutorial> getTutorials(Specification<Tutorial> spec, int page, int pageSize);
    //share
    String generateSharedLink(Long tutorialId);
    Map<Level, Integer> calculateStatisticsByLevel();



}
