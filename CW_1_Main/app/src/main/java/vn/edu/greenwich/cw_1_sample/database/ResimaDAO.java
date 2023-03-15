package vn.edu.greenwich.cw_1_sample.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;

import vn.edu.greenwich.cw_1_sample.models.Expense;
import vn.edu.greenwich.cw_1_sample.models.Trip;

public class ResimaDAO {
    protected ResimaDbHelper resimaDbHelper;
    protected SQLiteDatabase dbWrite, dbRead;

    public ResimaDAO(Context context) {
        resimaDbHelper = new ResimaDbHelper(context);

        dbRead = resimaDbHelper.getReadableDatabase();
        dbWrite = resimaDbHelper.getWritableDatabase();
    }

    public void close() {
        dbRead.close();
        dbWrite.close();
    }

    public void reset() {
        resimaDbHelper.onUpgrade(dbWrite, 0, 0);
    }
    // Trip.

    public long insertTrip(Trip trip) {
        ContentValues values = getTripValues(trip);

        return dbWrite.insert(TripEntry.TABLE_NAME, null, values);
    }

    public ArrayList<Trip> getTripList(Trip trip, String orderByColumn, boolean isDesc) {
        String orderBy = getOrderByString(orderByColumn, isDesc);

        String selection = null;
        String[] selectionArgs = null;

        if (trip != null) {
            selection = "";
            ArrayList<String> conditionList = new ArrayList<String>();

            if (trip.getTripName() != null && !trip.getTripName().trim().isEmpty()) {
                selection += " AND " + TripEntry.COL_TRIP_NAME + " LIKE ?";
                conditionList.add("%" + trip.getTripName() + "%");
            }

            if (trip.getDestination() != null && !trip.getDestination().trim().isEmpty()) {
                selection += " AND " + TripEntry.COL_DESTINATION + " LIKE ?";
                conditionList.add("%" + trip.getDestination() + "%");
            }

            if (trip.getTripDate() != null && !trip.getTripDate().trim().isEmpty()) {
                selection += " AND " + TripEntry.COL_TRIP_DATE + " LIKE ?";
                conditionList.add("%" + trip.getTripDate() + "%");
            }

            if (trip.getDescription() != null && !trip.getDescription().trim().isEmpty()) {
                selection += " AND " + TripEntry.COL_DESCRIPTION + " = ?";
                conditionList.add(trip.getDescription());
            }

            if (trip.getBudgetRange() != null && !trip.getBudgetRange().trim().isEmpty()) {
                selection += " AND " + TripEntry.COL_BUDGET_RANGE + " = ?";
                conditionList.add(trip.getBudgetRange());
            }

            if (trip.getImportanceNote() != null && !trip.getImportanceNote().trim().isEmpty()) {
                selection += " AND " + TripEntry.COL_IMPORTANT_NOTE + " = ?";
                conditionList.add(trip.getImportanceNote());
            }

            if (trip.getRequiresRiskAssessment() != -1) {
                selection += " AND " + TripEntry.COL_REQUIRES_RISK_ASSESSMENT + " = ?";
                conditionList.add(String.valueOf(trip.getRequiresRiskAssessment()));
            }

            if (!selection.trim().isEmpty()) {
                selection = selection.substring(5);
            }

            selectionArgs = conditionList.toArray(new String[conditionList.size()]);
        }

        return getTripFromDB(null, selection, selectionArgs, null, null, orderBy);
    }

    public Trip getTripById(long id) {
        String selection = TripEntry.COL_ID + " = ?";
        String[] selectionArgs = {String.valueOf(id)};

        return getTripFromDB(null, selection, selectionArgs, null, null, null).get(0);
    }

    public long updateTrip(Trip trip) {
        ContentValues values = getTripValues(trip);

        String selection = TripEntry.COL_ID + " = ?";
        String[] selectionArgs = {String.valueOf(trip.getTripId())};

        return dbWrite.update(TripEntry.TABLE_NAME, values, selection, selectionArgs);
    }

    public long deleteTrip(long id) {
        String selection = TripEntry.COL_ID + " = ?";
        String[] selectionArgs = {String.valueOf(id)};

        return dbWrite.delete(TripEntry.TABLE_NAME, selection, selectionArgs);
    }

    protected String getOrderByString(String orderByColumn, boolean isDesc) {
        if (orderByColumn == null || orderByColumn.trim().isEmpty())
            return null;

        if (isDesc)
            return orderByColumn.trim() + " DESC";

        return orderByColumn.trim();
    }

    protected ContentValues getTripValues(Trip trip) {
        ContentValues values = new ContentValues();

        values.put(TripEntry.COL_TRIP_NAME, trip.getTripName());
        values.put(TripEntry.COL_DESTINATION, trip.getDestination());
        values.put(TripEntry.COL_TRIP_DATE, trip.getTripDate());
        values.put(TripEntry.COL_DESCRIPTION, trip.getDescription());
        values.put(TripEntry.COL_BUDGET_RANGE, trip.getBudgetRange());
        values.put(TripEntry.COL_IMPORTANT_NOTE, trip.getImportanceNote());
        values.put(TripEntry.COL_REQUIRES_RISK_ASSESSMENT, trip.getRequiresRiskAssessment());

        return values;
    }

    protected ArrayList<Trip> getTripFromDB(String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy) {
        ArrayList<Trip> list = new ArrayList<>();

        Cursor cursor = dbRead.query(TripEntry.TABLE_NAME, columns, selection, selectionArgs, groupBy, having, orderBy);

        while (cursor.moveToNext()) {
            Trip tripItem = new Trip();

            tripItem.setTripId(cursor.getLong(cursor.getColumnIndexOrThrow(TripEntry.COL_ID)));
            tripItem.setTripName(cursor.getString(cursor.getColumnIndexOrThrow(TripEntry.COL_TRIP_NAME)));
            tripItem.setDestination(cursor.getString(cursor.getColumnIndexOrThrow(TripEntry.COL_DESTINATION)));
            tripItem.setTripDate(cursor.getString(cursor.getColumnIndexOrThrow(TripEntry.COL_TRIP_DATE)));
            tripItem.setDescription(cursor.getString(cursor.getColumnIndexOrThrow(TripEntry.COL_DESCRIPTION)));
            tripItem.setBudgetRange(cursor.getString(cursor.getColumnIndexOrThrow(TripEntry.COL_BUDGET_RANGE)));
            tripItem.setImportanceNote(cursor.getString(cursor.getColumnIndexOrThrow(TripEntry.COL_IMPORTANT_NOTE)));
            tripItem.setRequiresRiskAssessment(cursor.getInt(cursor.getColumnIndexOrThrow(TripEntry.COL_REQUIRES_RISK_ASSESSMENT)));

            list.add(tripItem);
        }

        cursor.close();

        return list;
    }

    // Expense.

    public long insertExpense(Expense expense) {
        ContentValues values = getExpenseValues(expense);

        return dbWrite.insert(ExpenseEntry.TABLE_NAME, null, values);
    }

    public ArrayList<Expense> getExpenseList(Expense expense, String orderByColumn, boolean isDesc) {
        String orderBy = getOrderByString(orderByColumn, isDesc);

        String selection = null;
        String[] selectionArgs = null;

        if (expense != null) {
            selection = "";
            ArrayList<String> conditionList = new ArrayList<String>();

            if (expense.getExpenseCost() != null && !expense.getExpenseCost().trim().isEmpty()) {
                selection += " AND " + ExpenseEntry.EXPENSE_COST + " LIKE ?";
                conditionList.add("%" + expense.getExpenseCost() + "%");
            }

            if (expense.getExpenseService() != null && !expense.getExpenseService().trim().isEmpty()) {
                selection += " AND " + ExpenseEntry.EXPENSE_SERVICE + " = ?";
                conditionList.add(expense.getExpenseService());
            }

            if (expense.getExpenseTime() != null && !expense.getExpenseTime().trim().isEmpty()) {
                selection += " AND " + ExpenseEntry.EXPENSE_TIME + " = ?";
                conditionList.add(expense.getExpenseTime());
            }

            if (expense.getExpensePlace() != null && !expense.getExpensePlace().trim().isEmpty()) {
                selection += " AND " + ExpenseEntry.EXPENSE_PLACE + " = ?";
                conditionList.add(expense.getExpensePlace());
            }

            if (expense.getTripId() != -1) {
                selection += " AND " + ExpenseEntry.TRIP_ID + " = ?";
                conditionList.add(String.valueOf(expense.getTripId()));
            }

            if (!selection.trim().isEmpty()) {
                selection = selection.substring(5);
            }

            selectionArgs = conditionList.toArray(new String[conditionList.size()]);
        }

        return getExpenseFromDB(null, selection, selectionArgs, null, null, orderBy);
    }

    public Expense getExpenseById(long id) {
        String selection = ExpenseEntry.COL_ID + " = ?";
        String[] selectionArgs = {String.valueOf(id)};

        return getExpenseFromDB(null, selection, selectionArgs, null, null, null).get(0);
    }

    public long updateExpense(Expense expense) {
        ContentValues values = getExpenseValues(expense);

        String selection = ExpenseEntry.COL_ID + " = ?";
        String[] selectionArgs = {String.valueOf(expense.getId())};

        return dbWrite.update(ExpenseEntry.TABLE_NAME, values, selection, selectionArgs);
    }

    public long deleteExpense(long id) {
        String selection = ExpenseEntry.COL_ID + " = ?";
        String[] selectionArgs = {String.valueOf(id)};

        return dbWrite.delete(ExpenseEntry.TABLE_NAME, selection, selectionArgs);
    }

    protected ContentValues getExpenseValues(Expense expense) {
        ContentValues values = new ContentValues();

        values.put(ExpenseEntry.EXPENSE_COST, expense.getExpenseCost());
        values.put(ExpenseEntry.EXPENSE_SERVICE, expense.getExpenseService());
        values.put(ExpenseEntry.EXPENSE_TIME, expense.getExpenseTime());
        values.put(ExpenseEntry.EXPENSE_PLACE, expense.getExpensePlace());
        values.put(ExpenseEntry.TRIP_ID, expense.getTripId());

        return values;
    }

    protected ArrayList<Expense> getExpenseFromDB(String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy) {
        ArrayList<Expense> list = new ArrayList<>();

        Cursor cursor = dbRead.query(ExpenseEntry.TABLE_NAME, columns, selection, selectionArgs, groupBy, having, orderBy);

        while (cursor.moveToNext()) {
            Expense expenseItem = new Expense();

            expenseItem.setId(cursor.getLong(cursor.getColumnIndexOrThrow(ExpenseEntry.COL_ID)));
            expenseItem.setExpenseCost(cursor.getString(cursor.getColumnIndexOrThrow(ExpenseEntry.EXPENSE_COST)));
            expenseItem.setExpenseService(cursor.getString(cursor.getColumnIndexOrThrow(ExpenseEntry.EXPENSE_SERVICE)));
            expenseItem.setExpenseTime(cursor.getString(cursor.getColumnIndexOrThrow(ExpenseEntry.EXPENSE_TIME)));
            expenseItem.setExpensePlace(cursor.getString(cursor.getColumnIndexOrThrow(ExpenseEntry.EXPENSE_PLACE)));
            expenseItem.setTripId(cursor.getLong(cursor.getColumnIndexOrThrow(ExpenseEntry.TRIP_ID)));

            list.add(expenseItem);
        }

        cursor.close();

        return list;
    }
}