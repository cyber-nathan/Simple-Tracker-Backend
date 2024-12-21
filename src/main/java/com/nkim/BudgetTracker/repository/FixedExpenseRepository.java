package com.nkim.BudgetTracker.repository;

import com.nkim.BudgetTracker.model.FixedExpense;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FixedExpenseRepository extends JpaRepository<FixedExpense, Long> {

}
