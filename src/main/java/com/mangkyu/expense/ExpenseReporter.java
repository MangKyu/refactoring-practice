package com.mangkyu.expense;

import static com.mangkyu.expense.Expense.Type.BREAKFAST;
import static com.mangkyu.expense.Expense.Type.DINNER;

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
        if (isMeal(expense)) {
            mealExpenses += expense.amount;
        }

        total += expense.amount;
    }

    boolean isMeal(Expense expense) {
        return expense.type == BREAKFAST || expense.type == DINNER;
    }

    public void addExpense(Expense expense) {
        expenses.add(expense);
    }
}