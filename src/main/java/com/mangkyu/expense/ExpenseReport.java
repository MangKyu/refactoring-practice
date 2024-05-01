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
        calculateExpenses();
        printHeader(printer);
        printExpenses(printer);
        printTotal(printer);
    }

    private void printExpenses(ReportPrinter printer) {
        for (Expense expense : expenses) {
            printer.print(String.format("%s\t%s\t$%.02f\n",
                    ((expense.type == DINNER && expense.amount > 5000)
                            || (expense.type == BREAKFAST && expense.amount > 1000)) ? "X" : " ",
                toExpenseName(expense), toDollars(expense.amount)));
        }
    }

    private void printHeader(ReportPrinter printer) {
        printer.print("Expenses " + getDate() + "\n");
    }

    private void printTotal(ReportPrinter printer) {
        printer.print(String.format("\nMeal expenses $%.02f", toDollars(mealExpenses)));
        printer.print(String.format("\nTotal $%.02f", toDollars(total)));
    }

    private String toExpenseName(Expense expense) {
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
        return name;
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
