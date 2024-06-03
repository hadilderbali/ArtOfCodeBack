package com.artofcode.artofcodebck.Controllers;

import com.artofcode.artofcodebck.Entities.*;
import com.artofcode.artofcodebck.Repositories.BadWordRepository;
import com.artofcode.artofcodebck.Repositories.ICommentRepository;
import com.artofcode.artofcodebck.Services.Tutorial.ITutorialService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@CrossOrigin("http://localhost:4200/api")
@RequestMapping("/tutorial")
public class TutorialRestController {
    private final ITutorialService TutorialService;

    @GetMapping("/getAllTutorials")
    public List<Tutorial> getAllTutorials() {

        return TutorialService.getAllTutorials();
    }

    @PostMapping("/addOrUpdate")
    public Tutorial addOrUpdateTutorial(@RequestBody Tutorial tutorial) {
        String Video = tutorial.getVideo();
        String video = tutorial.getVideo();

        // Vérifier si la chaîne vidéo est suffisamment longue avant d'extraire une sous-chaîne
        if (video.length() > 12) {
            tutorial.setVideo(video.substring(12));
        } else {
            // Gérer le cas où la chaîne vidéo est trop courte
            // Vous pouvez consigner un avertissement ou lever une exception, selon vos besoins
        }

        return TutorialService.addOrUpdateTutorial(tutorial);
    }

    @GetMapping("/getTutorialById/{idTutorial}")
    public Tutorial getTutorialById(@PathVariable long idTutorial) {
        return TutorialService.getTutorialById(idTutorial);

    }

    @PutMapping("/updateTutorial/{id}")
    public Tutorial updateTutorial(@RequestBody Tutorial tutorial, @PathVariable("id") Long id) {
        return TutorialService.updateTutorial(tutorial, id);
    }

    @DeleteMapping("/delete/{idTutorial}")
    public void deleteTutorial(@PathVariable long idTutorial) {

        TutorialService.deleteTutorial(idTutorial);
    }


//like and dislike



    @PostMapping("/{tutorialId}/like")
    public ResponseEntity<Void> likeTutorial(@PathVariable Long tutorialId) {
        TutorialService.likeTutorial(tutorialId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{tutorialId}/dislike")
    public ResponseEntity<Void> dislikeTutorial(@PathVariable Long tutorialId) {
        TutorialService.dislikeTutorial(tutorialId);
        return ResponseEntity.ok().build();
    }

    //comment
/* @PostMapping("/{tutorialId}/comments")
 public ResponseEntity<Comment> addComment(@PathVariable Long tutorialId, @Valid @RequestBody Comment comment) {
     Comment savedComment = TutorialService.addComment(tutorialId, comment);
     return ResponseEntity.ok(savedComment);
 }*/
    private final BadWordRepository badWordRepository;
    private final ICommentRepository commentRepository;

    @GetMapping("/listbadword")
    public List<BadWord> listbadword(){
        return   badWordRepository.findAll();

    }
    @PostMapping("/{tutorialId}/comments/{userId}")
    public ResponseEntity<Comment> addCommentToTutorial(@PathVariable Long tutorialId, @PathVariable Long userId, @RequestBody Comment comment) {
        boolean commentAdded = TutorialService.addComment(tutorialId, userId, comment);
        if (commentAdded) {
            // Récupérer le commentaire ajouté avec l'ID généré
            Comment savedComment = commentRepository.findById(comment.getCommentId()).orElse(null);

            if (savedComment != null) {
                return ResponseEntity.ok(savedComment);
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }



    @GetMapping("/getAllTutorialsWithComments")
    public List<Tutorial> getAllTutorialsWithComments() {
        return TutorialService.getAllTutorials();
    }




















    //filter
@GetMapping("/pagedd")
public Page<Tutorial> getEventsPaged(
        @RequestParam(required = false) String level,
        @RequestParam(required = false) String tutorialCategory,
        @RequestParam(defaultValue = "1") int page,
        @RequestParam(defaultValue = "10") int pageSize
) {
    Specification<Tutorial> spec = Specification.where(null);

    if (level != null && !level.isEmpty()) {
        spec = spec.and((root, query, builder) ->
                builder.equal(root.get("level"), Level.valueOf(level.toUpperCase())));
    }
    if (tutorialCategory != null && !tutorialCategory.isEmpty()) {
        spec = spec.and((root, query, builder) ->
                builder.equal(root.get("tutorialCategory"), TutorialCategory.valueOf(tutorialCategory.toUpperCase())));
    }
    return TutorialService.getTutorials(spec, page, pageSize);
}
//share
@GetMapping("/{tutorialId}/share")
public ResponseEntity<String> shareTutorial(@PathVariable Long tutorialId) {
    String sharedLink = TutorialService.generateSharedLink(tutorialId);
    if (sharedLink != null) {
        return ResponseEntity.ok(sharedLink);
    } else {
        return ResponseEntity.notFound().build();
    }
}

    @GetMapping("/statistics-by-level")
    public ResponseEntity<Map<Level, Integer>> getStatisticsByLevel() {
        Map<Level, Integer> statisticsByLevel = TutorialService.calculateStatisticsByLevel();
        return ResponseEntity.ok(statisticsByLevel);
    }









}





