package com.artofcode.artofcodebck.Services;


import com.artofcode.artofcodebck.Domaine.Response;
import com.artofcode.artofcodebck.Entities.Blog;
import com.artofcode.artofcodebck.Entities.Rating;
import com.artofcode.artofcodebck.Repositories.IBlogRepository;
import com.artofcode.artofcodebck.Repositories.IRatingRepoository;
import com.artofcode.artofcodebck.user.User;
import com.artofcode.artofcodebck.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@RequiredArgsConstructor
@Service
public class RatingService {
    private final IRatingRepoository ratingRepo;
    private final IBlogRepository iBlogRepository;
    private final UserRepository userRepository;

    @Transactional
    public Response addRating(Integer id, long blogId, int rating) {
        Optional<Rating> existingRating = ratingRepo.findByUserIdAndBlogIdBlog(id, blogId);

        if (existingRating.isPresent()) {
            return new Response("You have already rated this blog.");
        }

        User user = userRepository.findById(id).orElse(null);
        Blog blog = iBlogRepository.findById(blogId).orElse(null);

        if (user != null && blog != null) {
            Rating newRating = new Rating();
            newRating.setUser(user);
            newRating.setBlog(blog);
            newRating.setRating(rating);
            ratingRepo.save(newRating);
            return new Response("Rating added successfully.");
        } else {
            return new Response("User or blog not found.");
        }
    }


    public List<Rating> Getallrating() {
        return ratingRepo.findAll();
    }



    // Method to calculate the overall rating for a blog based on its ratings
    public double calculateOverallRating(long blogId) {
        // Fetch all ratings for the specified blog from the database
        List<Rating> ratings = ratingRepo.findByBlogIdBlog(blogId);


        // Calculate the sum of ratings
        int sumOfRatings = 0;
        for (Rating rating : ratings) {
            sumOfRatings += rating.getRating();
        }

        // Calculate the overall rating using the formula
        double overallRating = 0.0;
        if (!ratings.isEmpty()) {
            overallRating = (double) sumOfRatings / ratings.size();
        }

        return overallRating;
    }

    // Common method to group ratings by category
    private Map<String, List<Rating>> groupRatingsByCategory(List<Rating> ratings) {
        Map<String, List<Rating>> ratingsPerCategory = new HashMap<>();
        for (Rating rating : ratings) {
            String category = String.valueOf(rating.getBlog().getBlogCategory());
            ratingsPerCategory.computeIfAbsent(category, k -> new ArrayList<>()).add(rating);
        }
        return ratingsPerCategory;
    }

    public Map<String, Double> calculateAverageRatingPerCategory() {
        Map<String, Double> averageRatings = new HashMap<>();
        List<Rating> ratings = ratingRepo.findAll();
        Map<String, List<Rating>> ratingsPerCategory = groupRatingsByCategory(ratings);

        // Calculate average rating for each category
        for (Map.Entry<String, List<Rating>> entry : ratingsPerCategory.entrySet()) {
            String category = entry.getKey();
            List<Rating> categoryRatings = entry.getValue();
            OptionalDouble average = categoryRatings.stream().mapToInt(Rating::getRating).average();
            averageRatings.put(category, average.orElse(0.0));
        }

        return averageRatings;
    }

    public Map<String, Integer> getTotalNumberOfRatingsPerCategory() {
        Map<String, Integer> totalRatings = new HashMap<>();
        List<Rating> ratings = ratingRepo.findAll();
        Map<String, List<Rating>> ratingsPerCategory = groupRatingsByCategory(ratings);

        // Calculate total number of ratings for each category
        for (Map.Entry<String, List<Rating>> entry : ratingsPerCategory.entrySet()) {
            String category = entry.getKey();
            List<Rating> categoryRatings = entry.getValue();
            totalRatings.put(category, categoryRatings.size());
        }

        return totalRatings;
    }

    public Map<String, Integer> getHighestRatedPerCategory() {
        Map<String, Integer> highestRatingsPerCategory = new HashMap<>();
        List<Rating> ratings = ratingRepo.findAll();
        Map<String, List<Rating>> ratingsPerCategory = groupRatingsByCategory(ratings);

        for (Map.Entry<String, List<Rating>> entry : ratingsPerCategory.entrySet()) {
            String category = entry.getKey();
            List<Rating> categoryRatings = entry.getValue();
            Integer highestRating = categoryRatings.stream().mapToInt(Rating::getRating).max().orElse(0);
            highestRatingsPerCategory.put(category, highestRating);
        }

        return highestRatingsPerCategory;
    }

    public Map<String, Integer> getLowestRatedPerCategory() {
        Map<String, Integer> lowestRatingsPerCategory = new HashMap<>();
        List<Rating> ratings = ratingRepo.findAll();
        Map<String, List<Rating>> ratingsPerCategory = groupRatingsByCategory(ratings);

        for (Map.Entry<String, List<Rating>> entry : ratingsPerCategory.entrySet()) {
            String category = entry.getKey();
            List<Rating> categoryRatings = entry.getValue();
            Integer lowestRating = categoryRatings.stream().mapToInt(Rating::getRating).min().orElse(0);
            lowestRatingsPerCategory.put(category, lowestRating);
        }

        return lowestRatingsPerCategory;
    }

}



