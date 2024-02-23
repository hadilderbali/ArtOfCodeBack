package com.artofcode.artofcodebck.Controllers;

import com.artofcode.artofcodebck.Entities.Tutorial;
import com.artofcode.artofcodebck.Services.ITutorialService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/tutorial")
public class TutorialController {
    private final ITutorialService TutorialService;
    @GetMapping("/getall")
    public List<Tutorial> retrieveAllTutorial() {

        return TutorialService.retrieveAllTutorial();
    }
    @PostMapping("/add")
    public Tutorial addTutorial(@RequestBody Tutorial tutorial) {

        return TutorialService.addTutorial(tutorial);

    }
    @PutMapping("/update")
    public Tutorial updateTutorial(@RequestBody Tutorial tutorial) {

        return TutorialService.updateTutorial(tutorial);
    }
    @GetMapping("/get/{idTutorial}")
    public Tutorial retrieveTutorial(@PathVariable long idTutorial) {
        return TutorialService.retrieveTutorial(idTutorial);

    }
    @DeleteMapping("/delete/{idTutorial}")
    public void deleteTutorial(@PathVariable long idTutorial) {

        TutorialService.deleteTutorial(idTutorial);
    }

}
