package com.cst438_project2.repository;
import com.cst438_project2.model.Categories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Date;
import java.util.List;
@Repository
public interface CategoriesRepository extends JpaRepository<Categories, Integer>{
}
