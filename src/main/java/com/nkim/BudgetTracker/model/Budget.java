package com.nkim.BudgetTracker.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "budgets")
@Data
@Setter
@Getter
@NoArgsConstructor
public class Budget {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private BigDecimal totalBalance;

    private BigDecimal afterExpense;

    private BigDecimal salary;

    private BigDecimal totalSpent;

    private String payPeriod;

    private String payReset;

    @OneToMany(mappedBy = "budget", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FixedExpense> fixedExpenses = new ArrayList<FixedExpense>();

    @OneToMany(mappedBy = "budget", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Saving> savings = new ArrayList<Saving>();

    @OneToMany(mappedBy = "budget", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Category> categories = new ArrayList<Category>();

    public Budget(BigDecimal totalBalance, BigDecimal afterExpense, BigDecimal salary, BigDecimal totalSpent, String payPeriod, String payReset) {
        this.totalBalance = totalBalance;
        this.afterExpense = afterExpense;
        this.salary = salary;
        this.totalSpent = totalSpent;
        this.payPeriod = payPeriod;
        this.payReset = payReset;
    }

    public void addCategory(Category category) {
        categories.add(category);
        category.setBudget(this);
    }
    public void addFixedExpense(FixedExpense fixedExpense) {
        fixedExpenses.add(fixedExpense);
        fixedExpense.setBudget(this);
    }

    public void addSaving(Saving saving) {
        savings.add(saving);
        saving.setBudget(this);
    }

    public void removeCategory(Category category) {
        categories.remove(category);
        category.setBudget(null);
    }
}
