package com.mangkyu.expense;

import static com.mangkyu.expense.Expense.Type.BREAKFAST;
import static com.mangkyu.expense.Expense.Type.DINNER;

abstract class Expense {

    String toExpenseName() {
        String name = "TILT";
        switch (type) {
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

    boolean isMeal() {
        return type == BREAKFAST || type == DINNER;
    }

    public enum Type {DINNER, BREAKFAST, CAR_RENTAL}

    ;
    public Type type;
    public int amount;

    public Expense(Type type, int amount) {
        this.type = type;
        this.amount = amount;
    }
}
