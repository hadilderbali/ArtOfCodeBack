package com.artofcode.artofcodebck.Controllers;

import com.artofcode.artofcodebck.Entities.Tutorial;
import com.artofcode.artofcodebck.Services.ITutorialService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequiredArgsConstructor
@RestController
@CrossOrigin("http://localhost:4200/api")
@RequestMapping("/tutorial")
public class TutorialController {
    private final ITutorialService TutorialService;
    @GetMapping("/findAllTutorials")
    public List<Tutorial> findAllTutorials() {

        return TutorialService.findAllTutorials();
    }
    @PostMapping("/addOrUpdate")
    public Tutorial addOrUpdateTutorial(@RequestBody Tutorial tutorial) {

        return TutorialService.addOrUpdateTutorial(tutorial);

    }

    @GetMapping("/get/{idTutorial}")
    public Tutorial retrieveTutorial(@PathVariable long idTutorial) {
        return TutorialService.findTutorialById(idTutorial);

    }
    @DeleteMapping("/delete/{idTutorial}")
    public void deleteTutorial(@PathVariable long idTutorial) {

        TutorialService.deleteTutorial(idTutorial);
    }



}
