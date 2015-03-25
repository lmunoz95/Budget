package com.cyanococcus.budget.app.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.cyanococcus.budget.app.data.BudgetContract.ExpenseEntry;
import com.cyanococcus.budget.app.model.Expense;

import java.util.ArrayList;

public class
        BudgetDbHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "budget.db";

    public BudgetDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_EXPENSE_TABLE = "CREATE TABLE " + ExpenseEntry.TABLE_NAME + " (" +
                ExpenseEntry._ID + " INTEGER PRIMARY KEY," +
                ExpenseEntry.COLUMN_LOCATION + " TEXT NOT NULL," +
                ExpenseEntry.COLUMN_QUANTITY + " REAL NOT NULL," +
                ExpenseEntry.COLUMN_INFODATE + " DATE NOT NULL," +
                ExpenseEntry.COLUMN_DESCRIPTION + " TEXT NOT NULL" +
                ");";

        db.execSQL(SQL_CREATE_EXPENSE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXITS " + ExpenseEntry.TABLE_NAME);
        onCreate(db);
    }

    public long insert(Expense expense) throws SQLException {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ExpenseEntry.COLUMN_LOCATION, expense.getLocation());
        values.put(ExpenseEntry.COLUMN_QUANTITY, expense.getQuantity());
        values.put(ExpenseEntry.COLUMN_INFODATE, expense.getDate());
        values.put(ExpenseEntry.COLUMN_DESCRIPTION, expense.getDescription());

        long id = db.insertOrThrow(ExpenseEntry.TABLE_NAME, null, values);
        db.close();

        return id;
    }

    public Expense get(long id) {
        SQLiteDatabase db = getReadableDatabase();

        String query = "SELECT " +
            ExpenseEntry._ID + "," +
            ExpenseEntry.COLUMN_LOCATION + "," +
            ExpenseEntry.COLUMN_QUANTITY + "," +
            ExpenseEntry.COLUMN_INFODATE + "," +
            ExpenseEntry.COLUMN_DESCRIPTION +
            " FROM " + ExpenseEntry.TABLE_NAME +
            " WHERE " + ExpenseEntry._ID + " = ?;";

        Cursor cursor = db.rawQuery(query, new String[] { String.valueOf(id) });

        return parse(cursor).get(0);
    }

    public ArrayList<Expense> fetchAll() {
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.query(ExpenseEntry.TABLE_NAME,
                new String[]{
                        ExpenseEntry._ID,
                        ExpenseEntry.COLUMN_LOCATION,
                        ExpenseEntry.COLUMN_QUANTITY,
                        ExpenseEntry.COLUMN_INFODATE,
                        ExpenseEntry.COLUMN_DESCRIPTION
                }, null, null, null, null, null);

        return parse(cursor);
    }

    public ArrayList<Expense> fetchAll(long initialDate, long endDate) {
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT " +
                        ExpenseEntry._ID + "," +
                        ExpenseEntry.COLUMN_LOCATION + "," +
                        ExpenseEntry.COLUMN_QUANTITY + "," +
                        ExpenseEntry.COLUMN_INFODATE + "," +
                        ExpenseEntry.COLUMN_DESCRIPTION +
                    " FROM " + ExpenseEntry.TABLE_NAME +
                    " WHERE " + ExpenseEntry.COLUMN_INFODATE + " BETWEEN ? AND ?"
                , new String[] {
                        String.valueOf(initialDate),
                        String.valueOf(endDate)
                });

        return parse(cursor);
    }

    private ArrayList<Expense> parse(Cursor cursor) {
        ArrayList<Expense> list = new ArrayList<>();

        if(cursor.getCount() > 0) {
            while(cursor.moveToNext()) {
                Expense e = new Expense();

                e.setId(cursor.getLong(0));
                e.setLocation(cursor.getString(1));
                e.setQuantity(cursor.getDouble(2));
                e.setDate(cursor.getLong(3));
                e.setDescription(cursor.getString(4));

                list.add(e);
            }
        }

        return list;
    }
}