package com.technologybit.todo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabasePasswordHelper extends SQLiteOpenHelper {

//    private static DatabasePasswordHelper instance;
//    private static final String PASS_PHASE = "!@#ABC!"; // password to encrypt

    private static final String DB_NAME = "PasswordManager.db";

    // password table
    private static final String TABLE_PASSWORD = "Password_Table";

    // columns for password table
    private static final String ID = "ID";
    private static final String USER = "USER";
    private static final String PASSWORD = "PASSWORD";

    private static final String CREATE_PASSWORD_TABLE = "CREATE TABLE " + TABLE_PASSWORD + " ("
            + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + USER + " TEXT, "
            + PASSWORD + " TEXT)";

//    static public synchronized DatabasePasswordHelper getInstance(Context context) {
//        if (instance == null) {
//            instance = new DatabasePasswordHelper(context);
//        }
//        return instance;
//    }

    public DatabasePasswordHelper(Context context) {
        super(context, DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_PASSWORD_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_PASSWORD);

        onCreate(sqLiteDatabase);
    }

    // create method to insert data in password table
    public boolean insertDataPassword(String user, String password) {

        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(USER, user);
        contentValues.put(PASSWORD, password);

        long result = db.insert(TABLE_PASSWORD, null, contentValues);

        return result != -1;
    }

    // create method to view data from password database
    public Cursor viewPassData() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_PASSWORD;

        return db.rawQuery(query, null);
    }

    // create method to get id
    public Cursor getItemID(String password, String user) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + ID + " FROM " + TABLE_PASSWORD + " WHERE "
                + USER + " = '" + user + "' AND "
                + PASSWORD + " = '" + password + "'";

        Log.i("User", user);
        Log.i("Pass", password);

        return db.rawQuery(query, null);
        // The Above Code Performs Same As Below
        // Cursor cursor = db.rawQuery(query, null);
        // return cursor;
    }

    // create method to delete data
    public void deleteData(int id, String password, String user) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + TABLE_PASSWORD + " WHERE "
                + ID + " = '" + id + "' AND "
                + USER + " = '" + user + "' AND "
                + PASSWORD + " = '" + password + "'";
        db.execSQL(query);
    }

    // update method to update data to python
    public boolean updateData(String id, String password, String user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ID, id);
        contentValues.put(USER, user);
        contentValues.put(PASSWORD, password);
        db.update(TABLE_PASSWORD, contentValues,
                "ID = ?",new String[] { id });
        return true;
    }

}
