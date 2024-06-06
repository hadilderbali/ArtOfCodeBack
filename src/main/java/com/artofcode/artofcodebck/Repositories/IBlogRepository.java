package com.artofcode.artofcodebck.Repositories;

import com.artofcode.artofcodebck.Entities.Blog;
import com.artofcode.artofcodebck.Entities.BlogCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IBlogRepository extends JpaRepository<Blog,Long> {
    Page<Blog> findByTitleContaining(String title, Pageable pageable);

    Page<Blog> findByBlogCategory(BlogCategory blogCategory, Pageable pageable);

    Page<Blog> findByTitleContainingAndBlogCategory(String title,  BlogCategory blogCategory, Pageable pageable);
}
