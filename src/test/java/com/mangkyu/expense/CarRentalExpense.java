package com.mangkyu.expense;

import static com.mangkyu.expense.Expense.Type.BREAKFAST;
import static com.mangkyu.expense.Expense.Type.CAR_RENTAL;
import static com.mangkyu.expense.Expense.Type.DINNER;

public class CarRentalExpense extends Expense {

    public CarRentalExpense(int amount) {
        super(CAR_RENTAL, amount);
    }

    @Override
    String toExpenseName() {
        return "Car Rental";
    }

    @Override
    boolean isMeal() {
        return false;
    }
}
