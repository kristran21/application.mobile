package vn.edu.greenwich.cw_1_sample.database;

public class ExpenseEntry {
    public static final String TABLE_NAME = "expense";
    public static final String COL_ID = "id";
    public static final String EXPENSE_COST = "expense_cost";
    public static final String EXPENSE_SERVICE = "expense_service";
    public static final String EXPENSE_TIME = "expense_time";
    public static final String EXPENSE_PLACE = "expense_place";
    public static final String TRIP_ID = "trip_id";

    public static final String SQL_CREATE_TABLE =
            "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
                    COL_ID + " INTEGER PRIMARY KEY," +
                    EXPENSE_COST + " TEXT NOT NULL," +
                    EXPENSE_SERVICE + " TEXT NOT NULL," +
                    EXPENSE_TIME + " TEXT NOT NULL," +
                    EXPENSE_PLACE + " INTEGER NOT NULL," +
                    TRIP_ID + " INTEGER NOT NULL," +
                    "FOREIGN KEY(" + TRIP_ID + ") " +
                    "REFERENCES " + TripEntry.TABLE_NAME + "(" + TripEntry.COL_ID + "))";

    public static final String SQL_DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
}