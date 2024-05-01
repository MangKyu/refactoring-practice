package com.mangkyu.expense;

import static com.mangkyu.expense.Expense.Type.BREAKFAST;

public class BreakfastExpense extends Expense {

    public BreakfastExpense(int amount) {
        super(BREAKFAST, amount);
    }

    @Override
    String toExpenseName() {
        return "Breakfast";
    }

    @Override
    boolean isMeal() {
        return true;
    }
}
