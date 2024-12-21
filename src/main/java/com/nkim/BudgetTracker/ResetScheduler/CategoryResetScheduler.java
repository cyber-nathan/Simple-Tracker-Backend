package com.nkim.BudgetTracker.ResetScheduler;

import com.nkim.BudgetTracker.service.CategoryService;
import jakarta.annotation.PostConstruct;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class CategoryResetScheduler {
    private final CategoryService categoryService;

    public CategoryResetScheduler(CategoryService categoryService) {
        this.categoryService = categoryService;
    }
//    @PostConstruct
//    public void testScheduler() {
//        System.out.println("Scheduler initialized!");
//        resetCategories();  // Call it directly
//    }

    // Reset categories for a specific budget (e.g., Budget ID 1) daily at midnight
    @Scheduled(cron = "0 0 0 1 * ?")
    public void resetCategories() {
        System.out.println("reset function");
        Long budgetId = 1L; // Replace with dynamic logic if needed
        categoryService.resetCategories(budgetId);
    }
}
