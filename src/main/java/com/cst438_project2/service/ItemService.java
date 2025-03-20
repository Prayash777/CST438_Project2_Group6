// package com.cst438_project2.service;
// import com.cst438_project2.model.Categories;
// import com.cst438_project2.model.Item;
// import com.cst438_project2.repository.CategoriesRepository;
// import com.cst438_project2.repository.TierRepository;
// import com.cst438_project2.repository.ItemRepository;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Service;
// import java.util.List;
// import java.util.Optional;

// @Service
// public class ItemService{
//     @Autowired
//     ItemRepository itemRepository;

//     public List<Item> getItems(){
//        return itemRepository.findAll();
//     }

//     public Item getItemById(int itemId){
//         return itemRepository.findById(itemId).orElseThrow(() -> new RuntimeException("Item not found with ID: " + itemId));
//     }
    
//     public void addItem( Item item){
//         itemRepository.save(item);
//     }
    
//     public void updateItem( Item item){
//         itemRepository.save(item);
//     }

//     public void deleteItem(int itemId){
//         itemRepository.deleteById(itemId);
//     }
// }