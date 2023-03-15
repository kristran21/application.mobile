package vn.edu.greenwich.cw_1_sample.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class Backup implements Serializable {
    protected Date _date;
    protected String _deviceName;
    protected ArrayList<Trip> trip;
    protected ArrayList<Expense> expense;

    public Backup(Date date, String deviceName, ArrayList<Trip> trips, ArrayList<Expense> expense) {
        _date = date;
        _deviceName = deviceName;
        trip = trips;
        expense = expense;
    }

    public Date get_date() {
        return _date;
    }

    public void set_date(Date _date) {
        this._date = _date;
    }

    public String get_deviceName() {
        return _deviceName;
    }

    public void set_deviceName(String _deviceName) {
        this._deviceName = _deviceName;
    }

    public ArrayList<Trip> getTrip() {
        return trip;
    }

    public void setTrip(ArrayList<Trip> trip) {
        this.trip = trip;
    }

    public ArrayList<Expense> getExpense() {
        return expense;
    }

    public void setExpense(ArrayList<Expense> expense) {
        this.expense = expense;
    }
}