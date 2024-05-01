package com.mangkyu.expense;

import java.util.ArrayList;
import java.util.List;

public class ExpenseReporter {

    List<Expense> expenses = new ArrayList<>();
    int total;
    int mealExpenses;

    public ExpenseReporter() {
    }

    void calculateExpenses() {
        for (Expense expense : expenses) {
            calculateExpense(expense);
        }
    }

    void calculateExpense(Expense expense) {
        if (expense.isMeal()) {
            mealExpenses += expense.amount;
        }

        total += expense.amount;
    }

    public void addExpense(Expense expense) {
        expenses.add(expense);
    }
}