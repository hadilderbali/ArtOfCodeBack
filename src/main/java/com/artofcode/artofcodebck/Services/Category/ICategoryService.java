package com.artofcode.artofcodebck.Services.Category;

import com.artofcode.artofcodebck.Entities.Category;

import java.util.List;

public interface ICategoryService {
     Category addCategory(Category category);
     Category updateCategroy(Category category);
     void deleteCategory(long id );
     List<Category> getallCategories();
     Category getCategoryById(long id);
}
