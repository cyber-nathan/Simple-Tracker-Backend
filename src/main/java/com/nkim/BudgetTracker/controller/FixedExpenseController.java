package com.nkim.BudgetTracker.controller;
import com.nkim.BudgetTracker.exception.ResourceNotFoundException;
import com.nkim.BudgetTracker.model.Budget;
import com.nkim.BudgetTracker.model.FixedExpense;
import com.nkim.BudgetTracker.repository.FixedExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "http://localhost:4200", methods={RequestMethod.GET, RequestMethod.PUT})
@RestController
@RequestMapping("/api/v1/")

public class FixedExpenseController {
    @Autowired
    private FixedExpenseRepository fixedExpenseRepository;

//    @GetMapping("/{budgetId}/fixed-expenses")
//    public List<FixedExpense> getFixedExpensesByBudgetId(@PathVariable Long budgetId) {
//        return fixedExpenseRepository.findByBudgetId(budgetId);
//    }
}
