package com.nkim.BudgetTracker.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String title;
    private BigDecimal total;
    private BigDecimal spent;
    private BigDecimal remaining;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "budget_id")
    @JsonIgnore // Prevents infinite recursion
    private Budget budget;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Transaction>  transactions = new ArrayList<Transaction>();

    // Getters and Setters

    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
        transaction.setCategory(this);
    }
}
