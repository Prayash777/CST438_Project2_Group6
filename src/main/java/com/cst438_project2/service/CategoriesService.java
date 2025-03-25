package com.cst438_project2.service;
import com.cst438_project2.model.Categories;
import com.cst438_project2.repository.CategoriesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.Date;

@Service
public class CategoriesService{
    @Autowired
    CategoriesRepository repository;

    public List<Categories> getCategories(){
        return repository.findAll();
    }

    public Categories getCategoriesById(int categoryId){
        return repository.findById(categoryId).orElseThrow(() -> new RuntimeException("Category not found with ID: " + categoryId));
    }
    
     
    public void addCategory( Categories category){
        repository.save(category);
    }
    
    public void updateCategory( Categories category){
        repository.save(category);
    }

    public void deleteCategory(int categoryId){
        repository.deleteById(categoryId);
    }
}