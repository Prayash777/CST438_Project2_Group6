package com.cst438_project2.repository;
import com.cst438_project2.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<Item, Integer> {
    
}
