package com.cst438_project2.repository;

import com.cst438_project2.model.TierEntry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TierListRepository extends JpaRepository<TierEntry, Integer> {
}
