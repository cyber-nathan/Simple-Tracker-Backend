package com.nkim.BudgetTracker.service;

import com.nkim.BudgetTracker.repository.CategoryRepository;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public void resetCategories(Long budgetId) {
        categoryRepository.resetCategoriesByBudgetId(budgetId);
        System.out.println("Categories for Budget ID " + budgetId + " have been reset.");
    }
}
