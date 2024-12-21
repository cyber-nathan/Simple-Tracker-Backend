package com.nkim.BudgetTracker.ResetScheduler;

import com.nkim.BudgetTracker.service.SavingService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class SavingScheduler {
    private final SavingService savingService;

    public SavingScheduler(SavingService savingService) {
        this.savingService = savingService;
    }

    @Scheduled(cron = "0 0 0 1 * ?")
    public void incrementSaving() {
        System.out.println("increment saving function");
        Long budgetId = 1L; // Replace with dynamic logic if needed
        savingService.incrementSavings(budgetId);
    }
}
