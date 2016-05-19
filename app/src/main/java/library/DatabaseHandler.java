package library;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.HashMap;
/**
 * Created by SpringRoll on 1/5/2016.
 */
public class DatabaseHandler extends SQLiteOpenHelper {
    private final static String LOG_TAG = DatabaseHandler.class.getSimpleName();
    //Database version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "capstone2015";

    // Table name
    private static final String TABLE_USER = "user";
    private static final String TABLE_EVENT = "event";
    private static final String TABLE_COURSE = "course";
    private static final String TABLE_MUTIL = "multi";
    private static final String TABLE_SIGNLE = "single";
    private static final String TABLE_REMINDER = "reminder";


    //User Table Columns namse
    private static final String KEY_USERNAME = "username";
    private static final String KEY_PASWWORD = "password";
    private static final String KEY_CREATED_AT = "created_at";

    /** Table Create Statement. */
    //User Table create statement
    private static final String CREATE_TABLE_USER = "CREATE TABLE " + TABLE_USER + "("
            + KEY_USERNAME + " PRIMARY KEY,"
            + KEY_CREATED_AT + " TEXT" + ")";

    public DatabaseHandler(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    /**
     * Creating databse tables
     * @param db Database instance from AWS
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(LOG_TAG, "onCreate");
        //Todo: Don't know yet! but use for create the user schedule
        db.execSQL(CREATE_TABLE_USER);
    }

    /**
     * Upgrading databse
     * @param db Database instance from AWS
     * @param oldVersion old database version
     * @param newVersion new database verson
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(LOG_TAG, "onUpgrader");
        //Todo: Don't know yet! http://www.learn2crack.com/2013/08/develop-android-login-registration-with-php-mysql.html/3
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);

        // Create tables again
        onCreate(db);
    }

    /**
     * Add the user information into AWS database
     * @param username username
     * @param createdAt register created time
     */
    public void addUser(String username, String createdAt){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_USERNAME,username);      //Username
        values.put(KEY_CREATED_AT, createdAt);      //Created At
        //values.put(KEY_PASWWORD,password);    //Password

        //Inserting Row
        db.insert(TABLE_USER,null,values);
        db.close();
    }

    public void addScheduleEvent(){

    }

    /**
     * Getting user data from AWS database
     * @return  return user information with username and password
     */
    public HashMap<String, String> getUserDetails(){
        HashMap<String,String> user = new HashMap<String,String>();
        String selectQuery = "SELECT  * FROM " + TABLE_USER;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        //Move to first row
        cursor.moveToFirst();
        if(cursor.getCount() > 0){
            user.put("username",cursor.getString(0));
            //user.put("password",cursor.getString(2));
            user.put("created_at",cursor.getString(1));
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
        Log.d(LOG_TAG, "logoutUser");
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows
        db.delete(TABLE_USER, null, null);
        db.close();
    }
}
