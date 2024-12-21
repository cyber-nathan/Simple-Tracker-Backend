package com.nkim.BudgetTracker.repository;

import com.nkim.BudgetTracker.model.Category;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    @Modifying
    @Transactional
    @Query("UPDATE Category c SET c.total = c.remaining + c.total, c.spent = 0, c.remaining = c.remaining + c.total WHERE c.budget.id = :budgetId")
    void resetCategoriesByBudgetId(@Param("budgetId") Long budgetId);
}