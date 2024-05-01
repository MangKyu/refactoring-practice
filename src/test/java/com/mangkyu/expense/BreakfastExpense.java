package com.mangkyu.expense;

import static com.mangkyu.expense.Expense.Type.BREAKFAST;

public class BreakfastExpense extends Expense {

    public BreakfastExpense(int amount) {
        super(BREAKFAST, amount);
    }
}
