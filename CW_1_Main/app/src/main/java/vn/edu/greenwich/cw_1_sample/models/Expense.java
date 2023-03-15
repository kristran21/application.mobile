package vn.edu.greenwich.cw_1_sample.models;

import java.io.Serializable;

public class Expense implements Serializable {
    protected long id;
    protected String expenseCost;
    protected String expenseService;
    protected String expenseTime;
    protected String expensePlace;
    protected long tripId;

    public Expense() {
    }

    public Expense(long id, String expenseCost, String expenseService, String expenseTime, String expensePlace, long tripId) {
        this.id = id;
        this.expenseCost = expenseCost;
        this.expenseService = expenseService;
        this.expenseTime = expenseTime;
        this.expensePlace = expensePlace;
        this.tripId = tripId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getExpenseCost() {
        return expenseCost;
    }

    public void setExpenseCost(String expenseCost) {
        this.expenseCost = expenseCost;
    }

    public String getExpenseService() {
        return expenseService;
    }

    public void setExpenseService(String expenseService) {
        this.expenseService = expenseService;
    }

    public String getExpenseTime() {
        return expenseTime;
    }

    public void setExpenseTime(String expenseTime) {
        this.expenseTime = expenseTime;
    }

    public String getExpensePlace() {
        return expensePlace;
    }

    public void setExpensePlace(String expensePlace) {
        this.expensePlace = expensePlace;
    }

    public long getTripId() {
        return tripId;
    }

    public void setTripId(long tripId) {
        this.tripId = tripId;
    }
}