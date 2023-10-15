package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHandler extends SQLiteOpenHelper{
    // creating a constant variables for our database.
    // below variable is for our database name.
    private static final String DB_NAME = "accountID";

    // below int is our database version
    private static final int DB_VERSION = 1;

    // below variable is for our table name.
    private static final String TABLE_NAME = "mycourses";

    // below variable is for our user's username column.
    private static final String username_COL = "username";

    // below variable is for our user's password column
    private static final String password_COL = "password";

    // below variable id for our user's firstName column.
    private static final String firstName_COL = "firstName";

    // below variable for our user's lastName column.
    private static final String lastName_COL = "lastName";

    // below variable is for our user email column.
    private static final String email_COL = "email";

    // below variable for user's accountType column.
    private static final String accountType_COL = "accountType";

    // creating a constructor for our database handler.
    public DBHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    // below method is for creating a database by running a sqlite query
    @Override
    public void onCreate(SQLiteDatabase db) {
        // on below line we are creating
        // an sqlite query and we are
        // setting our column names
        // along with their data types.
        String query = "CREATE TABLE " + TABLE_NAME + " ("
                + username_COL + " TEXT, "
                + password_COL + " TEXT,"
                + firstName_COL + " TEXT,"
                + lastName_COL + " TEXT,"
                + email_COL + " TEXT)"
                + accountType_COL + "TEXT)";

        // at last we are calling a exec sql
        // method to execute above sql query
        db.execSQL(query);
    }

    // this method is use to add new course to our sqlite database.
    public void addNewUser(String userName, String passWord, String firstName, String lastName, String email, String accountType) {

        // on below line we are creating a variable for
        // our sqlite database and calling writable method
        // as we are writing data in our database.
        SQLiteDatabase db = this.getWritableDatabase();

        // on below line we are creating a
        // variable for content values.
        ContentValues values = new ContentValues();

        // on below line we are passing all values
        // along with its key and value pair.
        values.put(username_COL,userName);
        values.put(password_COL, passWord);
        values.put(firstName_COL, firstName);
        values.put(lastName_COL, lastName);
        values.put(email_COL, email);
        values.put(accountType_COL,accountType);

        // after adding all values we are passing
        // content values to our table.
        db.insert(TABLE_NAME, null, values);

        // at last we are closing our
        // database after adding database.
        db.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // this method is called to check if the table exists already.
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}

