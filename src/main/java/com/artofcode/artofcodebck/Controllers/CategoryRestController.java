package com.artofcode.artofcodebck.Controllers;

import com.artofcode.artofcodebck.Entities.Category;
import com.artofcode.artofcodebck.Services.Category.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
@CrossOrigin
public class CategoryRestController {

    @Autowired
    private ICategoryService categoryService;


    @GetMapping("/all")
    public List<Category> getAllCategories(){
        return categoryService.getallCategories();
    }

    @GetMapping("/{categoryId}")
    public Category getCategoryById(@PathVariable(name = "categoryId") Long id)
    {
        return categoryService.getCategoryById(id);
    }
    @PostMapping("/add")
    public Category addCategory(@RequestBody Category category){
       return categoryService.addCategory(category);
    }

    @PutMapping("/update")
    public Category updateCategory(@RequestBody Category category){
        return categoryService.updateCategroy(category);
    }

    @DeleteMapping("/delete/{categoryId}")
    public void deleteCategory(@PathVariable(name = "categoryId") Long id ){
        categoryService.deleteCategory(id);
    }
}
