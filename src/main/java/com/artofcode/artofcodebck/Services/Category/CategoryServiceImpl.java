package com.artofcode.artofcodebck.Services.Category;

import com.artofcode.artofcodebck.Entities.Category;
import com.artofcode.artofcodebck.Repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements ICategoryService {

    @Autowired
    private CategoryRepository categoryRepository;


    @Override
    public Category addCategory(final Category category){
        return categoryRepository.saveAndFlush(category);

    }
    @Override
    public Category updateCategroy(Category category)
    {
       return categoryRepository.saveAndFlush(category);
    }

    @Override
    public void deleteCategory(final long id)
    {
        Category category = getCategoryById(id);
        if (category != null)
        {
           categoryRepository.delete(category);
        }
    }
    @Override
    public List<Category> getallCategories(){

        return categoryRepository.findAll();
    }
    @Override
    public Category getCategoryById(long id){
        return categoryRepository.findById(id).orElse(null);
    }

}
