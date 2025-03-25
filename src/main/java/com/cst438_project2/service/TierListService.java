package com.cst438_project2.service;

import com.cst438_project2.model.Categories;
import com.cst438_project2.model.Item;
import com.cst438_project2.model.Tier;
import com.cst438_project2.model.TierEntry;

import com.cst438_project2.repository.CategoriesRepository;
import com.cst438_project2.repository.TierListRepository;

import jakarta.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Date;

@Service
public class TierListService {
    @Autowired
    private CategoriesRepository categoriesRepository;


    @Autowired
    private TierListRepository tierListRepository;

    // Categories CRUD operations
    public Categories saveCategories(Categories categories) {
        return categoriesRepository.save(categories);
    }

    public List<Categories> getCategories() {
        List<Categories> categories = categoriesRepository.findAll();
        System.out.println("Fetched categories:-------- " + categories);
        return categories;
    }

    public Optional<Categories> getCategoriesById(int id) {
        return categoriesRepository.findById(id);
    }

    // @PostConstruct
    // public void insertData() {
    //     List<Categories> categories = categoriesRepository.findAll();
    //     boolean exists = categories.stream().anyMatch(c -> c.getCategoryName().equals("Movies"));
    //     if (!exists) {
    //         Categories insertCategories = new Categories("Movies", new Date(), new Date());
    //         categoriesRepository.save(insertCategories);
    //     }
    //     categories = categoriesRepository.findAll();
    //     categories.forEach(category -> System.out.println("Category: " + category.getCategoryName()));
    // }

    public void deleteCategories(int id) {
        categoriesRepository.deleteById(id);
    }

    @Transactional
    public void saveTierEntry(TierEntry entry) {
        try {
            System.out.println("Attempting to save TierEntry: " + entry);
            tierListRepository.save(entry);
            System.out.println("Successfully saved TierEntry!");
        } catch (Exception e) {
            System.err.println("Error saving TierEntry: " + e.getMessage());
            e.printStackTrace();
        }
    }
}