package com.cst438_project2.service;

import com.cst438_project2.model.Categories;
import com.cst438_project2.model.Item;
import com.cst438_project2.model.Tier;
import com.cst438_project2.repository.CategoriesRepository;
import com.cst438_project2.repository.ItemRepository;
import com.cst438_project2.repository.TierRepository;

import jakarta.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Date;

@Service
public class TierListService {
    @Autowired
    private CategoriesRepository categoriesRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private TierRepository tierRepository;

    // Categories CRUD operations
    public Categories saveCategories(Categories categories) {
        return categoriesRepository.save(categories);
    }

    public List<Categories> getCategories() {
        return categoriesRepository.findAll();
    }

    public Optional<Categories> getCategoriesById(int id) {
        return categoriesRepository.findById(id);
    }

    @PostConstruct
    public void insertData() {
        List<Categories> categories = categoriesRepository.findAll();
        boolean exists = categories.stream().anyMatch(c -> c.getCategoryName().equals("Movies"));
        if (!exists) {
            Categories insertCategories = new Categories("Movies", new Date(), new Date());
            categoriesRepository.save(insertCategories);
        }
        categories = categoriesRepository.findAll();
        categories.forEach(category -> System.out.println("Category: " + category.getCategoryName()));
    }

    public void deleteCategories(int id) {
        categoriesRepository.deleteById(id);
    }

    // Item CRUD operations
    public Item saveItem(Item item, int categoriesId) {
        Optional<Categories> categoryOpt = categoriesRepository.findById(categoriesId);
        if (categoryOpt.isPresent()) {
            Categories categories = categoryOpt.get();
            item.setCategory(categories);
            return itemRepository.save(item);
        } else {
            return null;
        }
    }

    public List<Item> getItems() {
        return itemRepository.findAll();
    }

    public List<Item> getItemsByCategory(Categories categories) {
        return itemRepository.findByCategory(categories);
    }

    public Optional<Item> getItemsById(int id) {
        return itemRepository.findById(id);
    }

    public void deleteItem(int id) {
        itemRepository.deleteById(id);
    }

    // Tier CRUD operations
    public Tier saveTier(Tier tier) {
        return tierRepository.save(tier);
    }

    public List<Tier> getTiers() {
        return tierRepository.findAll();
    }

    public Optional<Tier> getTierById(int id) {
        return tierRepository.findById(id);
    }

    public void deleteTier(int id) {
        tierRepository.deleteById(id);
    }
}
