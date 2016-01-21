package library;

import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.HashMap;

import java.util.HashMap;
/**
 * Created by SpringRoll on 1/5/2016.
 */
public class DatabaseHandler extends SQLiteOpenHelper {

    //Database version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "capstone2015";

    // Login table name
    private static final String TABLE_USER = "user";

    //User Table Columns namse
    private static final String KEY_ID = "username";
    private static final String KEY_password = "passwd";

    public DatabaseHandler(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    /**
     * Creating databse tables
     * @param db Database instance from AWS
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        //Todo: Don't know yet! but use for create the user schedule
    }

    /**
     * Upgrading databse
     * @param db Database instance from AWS
     * @param oldVersion old database version
     * @param newVersion new database verson
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Todo: Don't know yet! http://www.learn2crack.com/2013/08/develop-android-login-registration-with-php-mysql.html/3
    }

    /**
     * Add the user information into AWS database
     * @param username username
     * @param passwd password
     */
    public void addUser(String username, String passwd){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_ID,username);    //Username
        values.put(KEY_password,passwd);    //Password

        //Inserting Row
        db.insert(TABLE_USER,null,values);
        db.close();
    }

    /**
     * Getting user data from AWS database
     * @return  return user information with username and password
     */
    public HashMap getUserDetails(){
        HashMap user = new HashMap();
        String selectQuery = "SELECT  * FROM " + TABLE_USER;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        //Move to first row
        cursor.moveToFirst();
        if(cursor.getCount() > 0){
            user.put("username",cursor.getString(1));
            user.put("passwd",cursor.getString(2));
        }
        cursor.close();
        db.close();
        return user;
    }

    /**
     * Getting user login status
     * @return return true if rows are there in table
     */
    public int getRowCount(){
        String countQuery = "SELECT  * FROM" + TABLE_USER;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int rowCount = cursor.getCount();
        db.close();
        cursor.close();
        return rowCount;
    }

    /**
     * Recreate databse
     * Delete all tables and creatte them again
     */
    public void resetTables(){
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows
        db.delete(TABLE_USER, null, null);
        db.close();
    }
}
