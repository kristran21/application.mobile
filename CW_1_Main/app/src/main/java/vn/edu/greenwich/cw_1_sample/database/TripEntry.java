package vn.edu.greenwich.cw_1_sample.database;

public class TripEntry {
    public static final String TABLE_NAME = "trip";
    public static final String COL_ID = "trip_id";
    public static final String COL_TRIP_NAME= "trip_name";
    public static final String COL_DESTINATION = "destination";
    public static final String COL_TRIP_DATE = "trip_date";
    public static final String COL_REQUIRES_RISK_ASSESSMENT= "requires_risk_assessment";
    public static final String COL_DESCRIPTION = "description";
    public static final String COL_IMPORTANT_NOTE = "importance_note";
    public static final String COL_BUDGET_RANGE = "budget_range";

    public static final String SQL_CREATE_TABLE =
            "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
                    COL_ID + " INTEGER PRIMARY KEY," +
                    COL_TRIP_NAME + " TEXT NOT NULL," +
                    COL_DESTINATION + " TEXT NOT NULL," +
                    COL_TRIP_DATE + " TEXT NOT NULL," +
                    COL_REQUIRES_RISK_ASSESSMENT + " INTEGER NOT NULL," +
                    COL_DESCRIPTION + " TEXT NOT NULL," +
                    COL_IMPORTANT_NOTE + " TEXT NOT NULL," +
                    COL_BUDGET_RANGE + " TEXT NOT NULL)";

    public static final String SQL_DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
}