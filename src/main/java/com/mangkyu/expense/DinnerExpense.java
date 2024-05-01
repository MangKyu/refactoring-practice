package com.mangkyu.expense;

import static com.mangkyu.expense.Expense.Type.DINNER;

public class DinnerExpense extends Expense {

    public DinnerExpense(int amount) {
        super(DINNER, amount);
    }

    @Override
    String toExpenseName() {
        return "Dinner";
    }

    @Override
    boolean isMeal() {
        return true;
    }
}
