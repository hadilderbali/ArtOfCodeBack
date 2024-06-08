package com.artofcode.artofcodebck.Controllers;


import com.artofcode.artofcodebck.Domaine.Response;
import com.artofcode.artofcodebck.Entities.Blog;

import com.artofcode.artofcodebck.Entities.BlogCategory;
import com.artofcode.artofcodebck.Services.IBlogService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FileUtils;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/blog")
@CrossOrigin("http://localhost:4200")
public class BlogController {
    final IBlogService iBlogService;

    @PostMapping("/addBlog")
    public ResponseEntity<Response> addBlog(@ModelAttribute Blog blog,
                                            @RequestParam(value = "image", required = false) MultipartFile image,
                                            @RequestParam("adminId") Integer adminId) {
        try {


            // Check if an image file is provided
            if (image != null && !image.isEmpty()) {
                // Save the uploaded image file and get the image URL
                String imageUrl = saveImage(image);
                // Set the image URL for the blog
                blog.setImageUrl(imageUrl);
            }

            // Save the blog
            Blog savedBlog = iBlogService.addBlog(blog, adminId);

            if (savedBlog != null) {
                return new ResponseEntity<>(new Response(""), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new Response("Blog not saved"), HttpStatus.BAD_REQUEST);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(new Response("Error adding blog"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Helper method to save the uploaded image file and return the image URL
    private String saveImage(MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename();

        // Define the directory path relative to the application's root directory
        String directoryPath = System.getProperty("user.dir") + "/src/main/webapp/BlogImg/";

        // Create the directory if it doesn't exist
        File directory = new File(directoryPath);
        if (!directory.exists()) {
            boolean created = directory.mkdirs(); // Creates directories if they do not exist
            if (!created) {
                // Log or handle the error if directory creation fails
                throw new IOException("Failed to create directory: " + directoryPath);
            }
        }

        File serverFile = new File(directoryPath + originalFilename);
        FileUtils.writeByteArrayToFile(serverFile, file.getBytes());

        return originalFilename; // Return the original filename
    }


    @PutMapping("/{id}")
    public ResponseEntity<Response> updateBlog(@PathVariable("id") long id, @RequestBody Blog updatedBlog) {
        iBlogService.updateBlog(id, updatedBlog);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @GetMapping("/get/{id}")
    public Blog getBlog(@PathVariable("id") long id) {
        return iBlogService.getBlog(id);

    }

    @GetMapping("/getblogs")
    public Page<Blog> findAll(@RequestParam(defaultValue = "0") int page,
                              @RequestParam(defaultValue = "4") int size) {
        return iBlogService.getBlogs(page, size);
    }

    @DeleteMapping("delete/{id}")
    public void deleteBlog(@PathVariable("id") Long id) {
        iBlogService.deleteBlog(id);
    }

    @GetMapping(path = "/ImgBlog/{blogId}")
    public byte[] getBlogPhoto(@PathVariable("blogId") Long blogId) throws Exception {
        System.out.println(blogId);
        return iBlogService.getJobBlogPhotoById(blogId);
    }


    @GetMapping("/search")
    public ResponseEntity<Page<Blog>> searchBlogs(
            @RequestParam(required = false) String query,
            @RequestParam(required = false) BlogCategory category,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size){
        Page<Blog> blogs = iBlogService.searchByQueryAndCategory(query, category, page,size);
        return new ResponseEntity<>(blogs, HttpStatus.OK);
    }



}