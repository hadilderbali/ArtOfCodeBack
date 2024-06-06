package com.artofcode.artofcodebck.Repositories;

import com.artofcode.artofcodebck.Entities.Rating;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IRatingRepoository extends JpaRepository<Rating,Long> {


    Optional<Rating> findByUserIdAndBlogIdBlog(Integer userId, long blogId);


    List<Rating> findByBlogIdBlog(long IdBlog);

}
