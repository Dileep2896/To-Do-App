package com.technologybit.todo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "Category.db";
    private static final String DB_TABLE = "Category_Table";

    //columns
    private static final String ID = "ID";
    private static final String C_NAME = "C_NAME";

    private static final String CREATE_TABLE = "CREATE TABLE " + DB_TABLE + " (" +
            ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            C_NAME + " TEXT " + ")";

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DB_TABLE);

        onCreate(sqLiteDatabase);
    }

    // create methods to insert the data
    public boolean insertData(String categoryName) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(C_NAME, categoryName);

        long result = db.insert(DB_TABLE, null, contentValues);

        return result != -1;
    }

    // create methods to view data from database
    public Cursor viewData() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + DB_TABLE;

        return db.rawQuery(query, null);
        // The Above Code Performs Same As Below
        // Cursor cursor = db.rawQuery(query, null);
        // return cursor;
    }

    // create method to get id
    public Cursor getItemID(String cName) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + ID + " FROM " + DB_TABLE
                + " WHERE " + C_NAME + " = '" + cName + "'";

        return db.rawQuery(query, null);
        // The Above Code Performs Same As Below
        // Cursor cursor = db.rawQuery(query, null);
        // return cursor;
    }

    // create method to delete data
    public void deleteData(int id, String cName) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + DB_TABLE + " WHERE " + ID +
                " = '" + id + "' AND " + C_NAME + " = '" + cName + "'";
        db.execSQL(query);
    }
}
