package com.cst438_project2.repository;

import com.cst438_project2.model.User;
import com.cst438_project2.model.Categories;
import com.cst438_project2.model.TierEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TierListRepository extends JpaRepository<TierEntry, Integer> {
    List<TierEntry> findByUserAndCategory(User user, Categories category);
    List<TierEntry> findTierEntriesByUser(User user);
}
