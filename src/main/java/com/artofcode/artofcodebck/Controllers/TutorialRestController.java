package com.artofcode.artofcodebck.Controllers;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.CrossOrigin;
import com.artofcode.artofcodebck.Entities.Level;
import com.artofcode.artofcodebck.Entities.Tutorial;
import com.artofcode.artofcodebck.Services.Tutorial.ITutorialService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public Tutorial updateTutorial(@RequestBody Tutorial tutorial,@PathVariable("id")Long id){
        return TutorialService.updateTutorial(tutorial,id);
    }
    @DeleteMapping("/delete/{idTutorial}")
    public void deleteTutorial(@PathVariable long idTutorial) {

        TutorialService.deleteTutorial(idTutorial);
    }

   /* @GetMapping("/filterByLevel")
    public ResponseEntity<List<Tutorial>> filterCoursesByLevel(@RequestParam Level level) {
        List<Tutorial> filteredCourses = TutorialService.filterCoursesByLevel(level);
        return new ResponseEntity<>(filteredCourses, HttpStatus.OK);
    }*/

    @GetMapping("/pagedd")
    public Page<Tutorial> getEventsPaged(@RequestParam(defaultValue = "1") int page,
                                         @RequestParam(defaultValue = "10") int pageSize) {
        return TutorialService.gets(page, pageSize);
    }


}
