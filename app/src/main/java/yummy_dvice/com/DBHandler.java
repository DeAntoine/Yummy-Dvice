package yummy_dvice.com;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.android.volley.RequestQueue;

import java.io.Serializable;
import java.util.ArrayList;

public class DBHandler extends SQLiteOpenHelper {

    // creating a constant variables for our database.
    // below variable is for our database name.
    private static final String DB_NAME = "yummydvice";

    // below int is our database version
    private static final int DB_VERSION = 1;

    // below variable is for our table name.
    private static final String TABLE_NAME = "User";

    private static DBHandler instance;

    // creating a constructor for our database handler.
    public DBHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    public static synchronized DBHandler getInstance(Context context) {
        if (instance == null) {
            instance = new DBHandler(context);
        }
        return instance;
    }

    // below method is for creating a database by running a sqlite query
    @Override
    public void onCreate(SQLiteDatabase db) {
        // on below line we are creating
        // an sqlite query and we are
        // setting our column names
        // along with their data types.

        String query = "CREATE TABLE User (\n" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "user_id TEXT ,\n" +
                "name TEXT,\n" +
                "review_count INT,\n" +
                "id_new TEXT,\n"+
                "favorites TEXT)";

        // at last we are calling a exec sql
        // method to execute above sql query
        db.execSQL(query);
    }

    public void emptyTable(){

        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("DELETE FROM " + TABLE_NAME);
    }

    public void addUser(User u){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put("user_id", u.user_id);
        values.put("id_new", u.id_new);
        values.put("name", u.name);
        values.put("review_count", u.review_count);
        values.put("favorites", u.favorites);

        // after adding all values we are passing
        // content values to our table.
        db.insert(TABLE_NAME, null, values);

        // at last we are closing our
        // database after adding database.
        db.close();
    }

    public User getUser(){

        SQLiteDatabase db = this.getReadableDatabase();

        // on below line we are creating a cursor with query to read data from database.
        Cursor c = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);

        if (c.getCount() > 0 && c.moveToFirst()) {

            // on below line we are creating a new array list.
            User u = new User(c.getString(1),
                    c.getString(2),
                    c.getInt(3),
                    c.getString(4),
                    c.getString(5)
            );
            c.close();
            db.close();
            return u;
        } else{
            db.close();
        }
        return null;
    }

    public void deleteUser(){

        SQLiteDatabase db = this.getReadableDatabase();

        db.delete(TABLE_NAME, "user_id != ?", new String[]{"argh"});
        db.close();
    }

    public void updateUser(String id, int val){

        SQLiteDatabase db = this.getReadableDatabase();

        ContentValues cValues = new ContentValues();
        cValues.put("review_count", val);

        db.update(TABLE_NAME, cValues,   " user_id = ?", new String[] {id});
        db.close();
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // this method is called to check if the table exists already.
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}


