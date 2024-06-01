package com.artofcode.artofcodebck.Controllers;

import com.artofcode.artofcodebck.user.User;
import com.artofcode.artofcodebck.user.UserRepository;
import com.artofcode.artofcodebck.Services.StatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/statistics")
@CrossOrigin("*")
public class StatisticController {

    @Autowired
    private StatisticService statisticService;
@Autowired
private UserRepository userRepository;
    @GetMapping("/competitions")
    public long getTotalCompetitions() {
        return statisticService.getTotalCompetitions();
    }
    
    @GetMapping("/candidacies")
    public long getTotalCandidacies() {
        return statisticService.getTotalCandidacies();
    }

    @GetMapping("/average-grade")
    public double getAverageGrade() {
        return statisticService.getAverageGrade();
    }

    // Ajoutez d'autres points de terminaison pour d'autres statistiques...
}
