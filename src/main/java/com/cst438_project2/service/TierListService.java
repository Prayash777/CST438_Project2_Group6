package com.cst438_project2.service;
import com.cst438_project2.model.Categories;
import com.cst438_project2.model.Item;
import com.cst438_project2.model.Tier;
import com.cst438_project2.repository.CategoriesRepository;
import com.cst438_project2.repository.ItemRepository;
import com.cst438_project2.repository.TierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

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

    public void deleteCategories(int id) {
        categoriesRepository.deleteById(id);
    }

    // Item CRUD operations
    public Item saveItem(Item item) {
        return itemRepository.save(item);
    }

    public List<Item> getItems() {
        return itemRepository.findAll();
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
