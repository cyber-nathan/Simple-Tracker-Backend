package com.nkim.BudgetTracker.service;

import com.nkim.BudgetTracker.repository.SavingRepository;
import org.springframework.stereotype.Service;

@Service
public class SavingService {
    private final SavingRepository savingRepository;

    public SavingService(SavingRepository savingRepository) {
        this.savingRepository = savingRepository;
    }

    public void incrementSavings(Long budgetId) {
        savingRepository.incrementSavingByBudgetId(budgetId);
        System.out.println("savings have been incremented for budget id " + budgetId);
    }
}
