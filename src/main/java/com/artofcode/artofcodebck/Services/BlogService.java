package com.artofcode.artofcodebck.Services;

import com.artofcode.artofcodebck.Entities.Blog;
import com.artofcode.artofcodebck.Entities.BlogCategory;
import com.artofcode.artofcodebck.Repositories.IBlogRepository;
import com.artofcode.artofcodebck.Repositories.IRatingRepoository;
import com.artofcode.artofcodebck.user.Role;
import com.artofcode.artofcodebck.user.User;
import com.artofcode.artofcodebck.user.UserRepository;
import jakarta.servlet.ServletContext;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Paths;

@Service
@RequiredArgsConstructor
public class BlogService implements IBlogService {
    final IBlogRepository iBlogRepository;
    final IRatingRepoository ratingRepo;
     final ServletContext context;
     final UserRepository userRepository;

    @Override
    public Blog addBlog(Blog blog, Integer adminId) {
    User admin= userRepository.findByIdAndRole(adminId, Role.ADMIN);
    blog.setUser(admin);
        return  iBlogRepository.save(blog);
    }

    @Override
    public void updateBlog(long idBlog, Blog updatedBlog) {
        Blog existingBlog = iBlogRepository.findById(idBlog).orElseThrow(() -> new RuntimeException("Job offer not found with ID: " + idBlog));


        existingBlog.setTitle(updatedBlog.getTitle());
        existingBlog.setContent(updatedBlog.getContent());
        existingBlog.setCreatedDate(updatedBlog.getCreatedDate());
        existingBlog.setUrl(updatedBlog.getUrl());


        iBlogRepository.save(existingBlog);


    }

    @Override
    public Page<Blog> getBlogs(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return iBlogRepository.findAll(pageable);
    }

    @Override
    public Blog getBlog(Long idBlog) {
       return iBlogRepository.findById(idBlog).orElse(null);

    }

    @Override
    public void deleteBlog(Long idBlog) {
        iBlogRepository.deleteById(idBlog);

    }
    @Override
    public byte[] getJobBlogPhotoById(Long blogId) throws Exception {
        Blog blog = iBlogRepository.findById(blogId).orElseThrow(() -> new Exception("image Job Offer not found"));
        return Files.readAllBytes(Paths.get(context.getRealPath("/BlogImg/") + blog.getImageUrl()));
    }
    @Override
    public Page<Blog> searchByQueryAndCategory(String query, BlogCategory category, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        if (query != null && category != null) {
            return iBlogRepository.findByTitleContainingAndBlogCategory(query, category, pageable);
        } else if (query != null) {
            return iBlogRepository.findByTitleContaining(query, pageable);
        } else if (category != null) {
            return iBlogRepository.findByBlogCategory(category, pageable);
        } else {
            // Both query and category are null, return all blogs with pagination
            return iBlogRepository.findAll(pageable);
        }
    }


}
