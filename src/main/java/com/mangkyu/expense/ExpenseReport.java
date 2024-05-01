package com.mangkyu.expense;

import static com.mangkyu.expense.Expense.Type.BREAKFAST;
import static com.mangkyu.expense.Expense.Type.DINNER;


public class ExpenseReport {

    private final ExpenseReporter expenseReporter = new ExpenseReporter();

    public void printReport(ReportPrinter printer) {
        expenseReporter.calculateExpenses();
        printHeader(printer);
        printExpenses(printer);
        printTotal(printer);
    }

    private void printExpenses(ReportPrinter printer) {
        for (Expense expense : expenseReporter.expenses) {
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
        printer.print(String.format("\nMeal expenses $%.02f", toDollars(expenseReporter.mealExpenses)));
        printer.print(String.format("\nTotal $%.02f", toDollars(expenseReporter.total)));
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
        expenseReporter.calculateExpenses();
    }

    private void calculateExpense(Expense expense) {

        expenseReporter.calculateExpense(expense);
    }

    private boolean isMeal(Expense expense) {
        return expenseReporter.isMeal(expense);
    }

    public void addExpense(Expense expense) {
        expenseReporter.addExpense(expense);
    }

    private String getDate() {
        return "9/12/2002";
    }
}
