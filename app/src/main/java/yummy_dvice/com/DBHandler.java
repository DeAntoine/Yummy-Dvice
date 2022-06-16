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
    private static final String TABLE_NAME = "business";

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

        String query = "CREATE TABLE Business (\n" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "business_id TEXT ,\n" +
                "name TEXT,\n" +
                "address TEXT,\n" +
                "city TEXT,\n" +
                "state TEXT, \n" +
                "postal_code TEXT,\n" +
                "latitude FLOAT,\n" +
                "longitude FLOAT,\n" +
                "image_id TEXT,\n" +
                "stars FLOAT)";

        // at last we are calling a exec sql
        // method to execute above sql query
        db.execSQL(query);
    }

    public void emptyTable(){

        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("DELETE FROM " + TABLE_NAME);
    }

    public void addRestaurant(String business_id, String name, String address, String city, String state, String postal_code, Double latitude, Double longitude, Double stars, String image_id){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put("business_id", business_id);
        values.put("name", name);
        values.put("address", address);
        values.put("city", city);
        values.put("state", state);
        values.put("postal_code", postal_code);
        values.put("latitude", latitude);
        values.put("longitude", longitude);
        values.put("stars", stars);
        values.put("image_id", image_id);

        // after adding all values we are passing
        // content values to our table.
        db.insert(TABLE_NAME, null, values);

        // at last we are closing our
        // database after adding database.
        db.close();
    }

    public void addRestaurant(ArrayList<Restaurant> restos){

        SQLiteDatabase db = this.getWritableDatabase();

        for(Restaurant r : restos){

            ContentValues values = new ContentValues();

            values.put("business_id", r.business_id);
            values.put("name", r.name);
            values.put("address", r.address);
            values.put("city", r.city);
            values.put("state", r.state);
            values.put("postal_code", r.postal_code);
            values.put("latitude", r.latitude);
            values.put("longitude", r.longitude);
            values.put("stars", r.stars);
            values.put("image_id", r.image_id);

            // after adding all values we are passing
            // content values to our table.
            db.insert(TABLE_NAME, null, values);

        }
        // at last we are closing our
        // database after adding database.
        db.close();
    }

    public ArrayList<Restaurant> getRestaurants(){

        SQLiteDatabase db = this.getReadableDatabase();

        // on below line we are creating a cursor with query to read data from database.
        Cursor cursorCourses = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);

        // on below line we are creating a new array list.
        ArrayList<Restaurant> courseModalArrayList = new ArrayList<>();

        // moving our cursor to first position.
        if (cursorCourses.moveToFirst()) {
            do {
                // on below line we are adding the data from cursor to our array list.
                courseModalArrayList.add(new Restaurant(cursorCourses.getString(0),
                        cursorCourses.getString(1),
                        cursorCourses.getString(2),
                        cursorCourses.getString(3),
                        cursorCourses.getString(4),
                        cursorCourses.getString(5),
                        cursorCourses.getDouble(6),
                        cursorCourses.getDouble(7),
                        cursorCourses.getFloat(8),
                        cursorCourses.getString(9),
                        cursorCourses.getInt(10),
                        cursorCourses.getString(11)));
            } while (cursorCourses.moveToNext());
            // moving our cursor to next.
        }
        // at last closing our cursor
        // and returning our array list.
        cursorCourses.close();

        return courseModalArrayList;
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // this method is called to check if the table exists already.
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}


