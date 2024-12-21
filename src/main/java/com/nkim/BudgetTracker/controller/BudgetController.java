package com.nkim.BudgetTracker.controller;

import com.nkim.BudgetTracker.exception.ResourceNotFoundException;
import com.nkim.BudgetTracker.model.*;
import com.nkim.BudgetTracker.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.util.List;
@RestController
@RequestMapping("/api/v1/")
@CrossOrigin
public class BudgetController {

    @Autowired
    private BudgetRepository budgetRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private FixedExpenseRepository fixedExpenseRepository;

    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private SavingRepository savingRepository;

    @GetMapping("/budgets")
    public List<Budget> getAllBudget() {
        return budgetRepository.findAll();
    }
    @GetMapping("/budget")
    public Budget getBudget() {
        return budgetRepository.findById(1L).orElseThrow(()-> new ResourceNotFoundException("Budget not exist with id"));
    }


    // create buget rest api
@PostMapping("/budget")
    public Budget createBudget(Budget budget) {
    System.out.println("this is budget" + budget);
        return budgetRepository.save(budget);

    }

    @PutMapping("/budget/{id}")
 public ResponseEntity<Budget> updateBudget(@PathVariable Long id, @RequestBody Budget budgetDetails ) {
        Budget budget = budgetRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Budget not exist with id: " + id));

        budget.setAfterExpense(budgetDetails.getTotalBalance().subtract(budget.getTotalBalance().subtract(budget.getAfterExpense())));
        budget.setTotalBalance(budgetDetails.getTotalBalance());
        budget.setSalary(budgetDetails.getSalary());
        budget.setPayPeriod(budgetDetails.getPayPeriod());
        budget.setPayReset(budgetDetails.getPayReset());


     Budget updateBudget = budgetRepository.save(budget);
        System.out.println("update budget: " + id + " " +  budgetDetails + " " + updateBudget);
     return ResponseEntity.ok(updateBudget);
 }

 @GetMapping("/budget/{id}/fixed_expense")
 public List<FixedExpense> getFixedExpense(@PathVariable long id) {
     Budget budget = budgetRepository.findById(id)
             .orElseThrow(() -> new ResourceNotFoundException("Budget not found with id: " + id));
     return budget.getFixedExpenses();
 }

 @PostMapping("/budget/{budgetId}/fixed_expense")
 public ResponseEntity<FixedExpense> addFixedExpense(@PathVariable long budgetId, @RequestBody FixedExpense fixedExpenseDetails) {
     Budget budget = budgetRepository.findById(budgetId)
             .orElseThrow(() -> new ResourceNotFoundException("Budget not exist with id: " + budgetId));

     // Set budget for the category and add to budget's categories list
     fixedExpenseDetails.setBudget(budget);  // Set the budget in the category
     budget.addFixedExpense(fixedExpenseDetails); // Add the category to the budget's categories list
     budget.setAfterExpense(budget.getAfterExpense().subtract(fixedExpenseDetails.getSpent()));
     FixedExpense savedFixedExpense = fixedExpenseRepository.save(fixedExpenseDetails);

     System.out.println("this is a saved fixed expense " + savedFixedExpense);


     return ResponseEntity.status(HttpStatus.CREATED).body(savedFixedExpense);

 }
    @DeleteMapping("/budget/{budgetId}/fixed_expense/{fixedExpenseId}")
    public ResponseEntity<FixedExpense> deleteFixedExpense(@PathVariable long budgetId, @PathVariable long fixedExpenseId) {
        System.out.println("Delete Item");


        Budget budget = budgetRepository.findById(budgetId)
                .orElseThrow(() -> new ResourceNotFoundException("Budget not exist with id: " + budgetId));

        FixedExpense fixedExpense = fixedExpenseRepository.findById(fixedExpenseId)
                .orElseThrow(() -> new ResourceNotFoundException("fixed expense not exist with id: " + fixedExpenseId));
        budget.setAfterExpense(budget.getAfterExpense().add(fixedExpense.getSpent()));

        fixedExpenseRepository.deleteById(fixedExpenseId);
       // System.out.println("this is cat " + category);


        return ResponseEntity.ok(fixedExpense);
    }

    @PutMapping("/budget/{budgetId}/fixed_expense/{fixedExpenseId}")
    public ResponseEntity<FixedExpense> editFixedExpense(@PathVariable Long budgetId, @PathVariable Long fixedExpenseId, @RequestBody FixedExpense fixedExpenseDetails ) {
        Budget budget = budgetRepository.findById(budgetId)
                .orElseThrow(() -> new ResourceNotFoundException("Budget not exist with id: " + budgetId));
        FixedExpense fixedExpense = fixedExpenseRepository.findById(fixedExpenseId)
                .orElseThrow(() -> new ResourceNotFoundException("fixed expense not exist with id: " + fixedExpenseId));

        budget.setAfterExpense(budget.getAfterExpense().add(fixedExpense.getSpent().subtract(fixedExpenseDetails.getSpent()) ));
        fixedExpense.setTitle(fixedExpenseDetails.getTitle());
        fixedExpense.setSpent(fixedExpenseDetails.getSpent());

        FixedExpense updateFixedExpense = fixedExpenseRepository.save(fixedExpense);
        return ResponseEntity.ok(updateFixedExpense);
    }

    @GetMapping("/budget/{id}/saving")
    public List<Saving> getSaving(@PathVariable long id) {
        Budget budget = budgetRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Budget not found with id: " + id));
        return budget.getSavings();
    }

    @PostMapping("/budget/{budgetId}/saving")
    public ResponseEntity<Saving> addSaving(@PathVariable long budgetId, @RequestBody Saving savingDetails) {
        Budget budget = budgetRepository.findById(budgetId)
                .orElseThrow(() -> new ResourceNotFoundException("Budget not exist with id: " + budgetId));

        // Set budget for the category and add to budget's categories list
        savingDetails.setBudget(budget);  // Set the budget in the category
        budget.addSaving(savingDetails); // Add the category to the budget's categories list

        Saving savedSaving = savingRepository.save(savingDetails);

        System.out.println("this is a saved Saving " + savedSaving);


        return ResponseEntity.status(HttpStatus.CREATED).body(savedSaving);

    }

    @DeleteMapping("/budget/{budgetId}/saving/{savingId}")
    public ResponseEntity<Saving> deleteSaving(@PathVariable long budgetId, @PathVariable long savingId) {
        System.out.println("Delete Item");


        Budget budget = budgetRepository.findById(budgetId)
                .orElseThrow(() -> new ResourceNotFoundException("Budget not exist with id: " + budgetId));

        Saving saving = savingRepository.findById(savingId)
                .orElseThrow(() -> new ResourceNotFoundException("saving not exist with id: " + savingId));

        savingRepository.deleteById(savingId);
        // System.out.println("this is cat " + category);


        return ResponseEntity.ok(saving);
    }

    @PutMapping("/budget/{budgetId}/saving/{savingId}")
    public ResponseEntity<Saving> editSaving(@PathVariable Long budgetId, @PathVariable Long savingId, @RequestBody Saving savingDetials ) {
        Budget budget = budgetRepository.findById(budgetId)
                .orElseThrow(() -> new ResourceNotFoundException("Budget not exist with id: " + budgetId));
        Saving saving = savingRepository.findById(savingId)
                .orElseThrow(() -> new ResourceNotFoundException("saving not exist with id: " + savingId));

        saving.setTitle(savingDetials.getTitle());
        saving.setAmount(savingDetials.getAmount());
        saving.setSaved(savingDetials.getSaved());

        Saving updateSaving = savingRepository.save(saving);
        System.out.println("update saving saved");
        return ResponseEntity.ok(updateSaving);
    }



    @GetMapping("/budget/{id}/category")
 public List<Category> getCategories(@PathVariable long id) {
     Budget budget = budgetRepository.findById(id)
             .orElseThrow(() -> new ResourceNotFoundException("Budget not found with id: " + id));
     return budget.getCategories();
 }
    // Add a new category to an existing budget
    @PostMapping("/budget/{id}/category")
    public ResponseEntity<Category> addCategoryToBudget(@PathVariable Long id,   @RequestBody Category category) {
        Budget budget = budgetRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Budget not exist with id: " + id));

        // Set budget for the category and add to budget's categories list
        category.setBudget(budget);  // Set the budget in the category
        budget.addCategory(category); // Add the category to the budget's categories list

        Category savedCat = categoryRepository.save(category);

        System.out.println("this is a saved cat " + savedCat);


        // Save the budget with the new categories
       // Budget updatedBudget = budgetRepository.save(budget);
        //System.out.println("category has been added " + category);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedCat);
    }

    @PutMapping("/budget/{budgetId}/category/{catId}")
    public ResponseEntity<Category> editCategory(@PathVariable Long budgetId, @PathVariable Long catId, @RequestBody Category categoryDetails ) {
        Budget budget = budgetRepository.findById(budgetId)
                .orElseThrow(() -> new ResourceNotFoundException("Budget not exist with id: " + budgetId));
        Category category = categoryRepository.findById(catId)
                        .orElseThrow(() -> new ResourceNotFoundException("category not found with id:" + catId));

        //budget.setAfterExpense(budget.getAfterExpense().add(fixedExpense.getSpent().subtract(fixedExpenseDetails.getSpent()) ));
        System.out.println("this is category details" + categoryDetails);
        category.setTitle(categoryDetails.getTitle());
        category.setTotal(categoryDetails.getTotal());
        category.setRemaining(category.getTotal().subtract(category.getSpent()));

        Category updateCategory = categoryRepository.save(category);
        return ResponseEntity.ok(updateCategory);
    }



    @DeleteMapping("/budget/{budgetId}/category/{catId}")
    public ResponseEntity<Category> deleteCategory(@PathVariable long budgetId, @PathVariable long catId) {
        System.out.println("Delete Item");


        Budget budget = budgetRepository.findById(budgetId)
                .orElseThrow(() -> new ResourceNotFoundException("Budget not exist with id: " + budgetId));

        Category category = categoryRepository.findById(catId)
                .orElseThrow(() -> new ResourceNotFoundException("Category not exist with id: " + catId));


        categoryRepository.deleteById(catId);
        System.out.println("this is cat " + category);


        return ResponseEntity.ok(category);
    }

    @PostMapping("/budget/{budgetId}/category/{catId}")
    public ResponseEntity<Transaction> AddTransaction(@PathVariable long budgetId, @PathVariable long catId, @RequestBody Transaction transactionDetails) {
        Category category = categoryRepository.findById(catId)
                .orElseThrow(() -> new ResourceNotFoundException("Budget not exist with id: " + catId));

        // Set budget for the category and add to budget's categories list
        transactionDetails.setCategory(category);  // Set the budget in the category
        category.setSpent(category.getSpent().add(transactionDetails.getSpent()) );
        category.setRemaining(category.getRemaining().subtract(transactionDetails.getSpent()));
        category.addTransaction(transactionDetails); // Add the category to the budget's categories list

        Transaction savedTransaction = transactionRepository.save(transactionDetails);


        return ResponseEntity.status(HttpStatus.CREATED).body(savedTransaction);
    }

    @DeleteMapping("/budget/{budgetId}/category/{catId}/transaction/{transactionId}")
    public ResponseEntity<Category> deleteTransaction(@PathVariable long budgetId, @PathVariable long catId, @PathVariable long transactionId) {
        System.out.println("Delete Item");


        Budget budget = budgetRepository.findById(budgetId)
                .orElseThrow(() -> new ResourceNotFoundException("Budget not exist with id: " + budgetId));

        Category category = categoryRepository.findById(catId)
                .orElseThrow(() -> new ResourceNotFoundException("Category not exist with id: " + catId));
        Transaction transaction = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new ResourceNotFoundException("Transaction not exist with id: " + transactionId));
        category.setSpent(category.getSpent().subtract(transaction.getSpent()) );
        category.setRemaining(category.getRemaining().add(transaction.getSpent()));
       transactionRepository.deleteById(transactionId);
        System.out.println("this is cat " + category);


        return ResponseEntity.ok(category);
    }

}
