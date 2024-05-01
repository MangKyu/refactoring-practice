package com.mangkyu.expense;

abstract class Expense {

    abstract String toExpenseName();

    abstract boolean isMeal();

    public enum Type {DINNER, BREAKFAST, CAR_RENTAL}

    ;
    public Type type;
    public int amount;

    public Expense(Type type, int amount) {
        this.type = type;
        this.amount = amount;
    }
}
