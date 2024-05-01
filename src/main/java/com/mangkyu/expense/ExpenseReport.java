package com.mangkyu.expense;

import static com.mangkyu.expense.Expense.Type.BREAKFAST;
import static com.mangkyu.expense.Expense.Type.DINNER;

import java.util.ArrayList;
import java.util.List;


public class ExpenseReport {
    private List<Expense> expenses = new ArrayList<>();
    private int total;
    private int mealExpenses;

    public void printReport(ReportPrinter printer) {
        printer.print("Expenses " + getDate() + "\n");

        calculateExpenses();

        for (Expense expense : expenses) {
            String name = "TILT";
            switch (expense.type) {
                case DINNER:
                    name = "Dinner";
                    break;
                case BREAKFAST:
                    name = "Breakfast";
                    break;
                case CAR_RENTAL:
                    name = "Car Rental";
                    break;
            }
            printer.print(String.format("%s\t%s\t$%.02f\n",
                    ((expense.type == DINNER && expense.amount > 5000)
                            || (expense.type == BREAKFAST && expense.amount > 1000)) ? "X" : " ",
                    name, toDollars(expense.amount)));
        }

        printer.print(String.format("\nMeal expenses $%.02f", toDollars(mealExpenses)));
        printer.print(String.format("\nTotal $%.02f", toDollars(total)));
    }

    private static double toDollars(int amount) {
        return amount / 100.0;
    }

    private void calculateExpenses() {
        for (Expense expense : expenses) {
            calculateExpense(expense);
        }
    }

    private void calculateExpense(Expense expense) {
        if (isMeal(expense))
            mealExpenses += expense.amount;

        total += expense.amount;
    }

    private boolean isMeal(Expense expense) {
        return expense.type == BREAKFAST || expense.type == DINNER;
    }

    public void addExpense(Expense expense) {
        expenses.add(expense);
    }

    private String getDate() {
        return "9/12/2002";
    }
}
