package com.nkim.BudgetTracker.repository;

import com.nkim.BudgetTracker.model.Saving;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SavingRepository extends JpaRepository<Saving, Long> {
    @Modifying
    @Transactional
    @Query("UPDATE Saving s SET s.saved = s.saved + s.amount WHERE s.budget.id = :budgetId")
    void incrementSavingByBudgetId(@Param("budgetId") Long budgetId);
}
