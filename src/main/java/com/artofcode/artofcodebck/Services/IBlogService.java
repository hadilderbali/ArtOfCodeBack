package com.artofcode.artofcodebck.Services;

import com.artofcode.artofcodebck.Entities.Blog;
import com.artofcode.artofcodebck.Entities.BlogCategory;
import org.springframework.data.domain.Page;


public interface IBlogService {
    Blog addBlog(Blog blog,Integer adminId);
    void updateBlog(long idBlog,Blog updatedBlog);
    Page<Blog> getBlogs(int page, int size);
    Blog getBlog(Long idBlog);
    void  deleteBlog(Long idBlog);
    public byte[] getJobBlogPhotoById(Long blogId) throws Exception;
    Page<Blog> searchByQueryAndCategory(String query, BlogCategory category, int page,int size );
}
