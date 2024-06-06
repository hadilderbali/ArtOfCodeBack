package com.artofcode.artofcodebck.Controllers;

import com.artofcode.artofcodebck.Entities.Blog;
import com.artofcode.artofcodebck.Entities.Rating;
import com.artofcode.artofcodebck.Services.RatingService;
import com.artofcode.artofcodebck.Domaine.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/Rating")
@RequiredArgsConstructor
@CrossOrigin("http://localhost:4200")

public class RatingController {

    private final RatingService ratingService;

    @PostMapping("/add/{userId}/{blogId}/{rating}")
    public ResponseEntity<Response> addRating(@PathVariable("userId") int userId,
                                              @PathVariable("blogId") long blogId,
                                              @PathVariable("rating") int rating) {
        Response response = ratingService.addRating(userId, blogId, rating);

        if ("Rating added successfully.".equals(response.getMessage())) {
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } else if ("You have already rated this blog.".equals(response.getMessage())) {
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/get")
    public List<Rating> Getallrating() {
        return ratingService.Getallrating();
    }


    @GetMapping("/overall/{blogId}")
    public ResponseEntity<Double> getOverallRating(@PathVariable("blogId") long blogId) {
        double overallRating = ratingService.calculateOverallRating(blogId);
        return ResponseEntity.ok(overallRating);
    }

    @GetMapping("/average")
    public Map<String, Double> getAverageRatingPerCategory() {
        return ratingService.calculateAverageRatingPerCategory();
    }

    @GetMapping("/total")
    public Map<String, Integer> getTotalNumberOfRatingsPerCategory() {
        return ratingService.getTotalNumberOfRatingsPerCategory();
    }

    @GetMapping("/highest")
    public Map<String, Integer> getHighestRatedBlogPerCategory() {
        return ratingService.getHighestRatedPerCategory();
    }

    @GetMapping("/lowest")
    public Map<String, Integer> getLowestRatedBlogPerCategory() {
        return ratingService.getLowestRatedPerCategory();
    }}