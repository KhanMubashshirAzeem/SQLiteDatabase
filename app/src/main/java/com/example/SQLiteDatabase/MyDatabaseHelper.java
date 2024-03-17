package com.example.SQLiteDatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class MyDatabaseHelper extends SQLiteOpenHelper {

    private final Context context;
    private static final String DATABASE_NAME = "BookLibrary.db"; // Name of the database
    private static final int DATABASE_VERSION = 1; // Database version
    private static final String TABLE_NAME = "my_library"; // Name of the table
    private static final String COLUMN_ID = "id"; // Column name for the ID
    private static final String COLUMN_TITLE = "book_title"; // Column name for the book title
    private static final String COLUMN_AUTHOR = "book_author"; // Column name for the book author
    private static final String COLUMN_PAGES = "book_pages"; // Column name for the number of pages in the book

    // Constructor for MyDatabaseHelper
    public MyDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION); // Call the superclass constructor
        this.context = context; // Set the context
    }

    // Method called when the database is created for the first time
    @Override
    public void onCreate(SQLiteDatabase db) {
        // SQL query to create the table with specified columns
        String query = "CREATE TABLE " + TABLE_NAME + "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + // Define ID column as primary key
                COLUMN_TITLE + " TEXT, " + // Define column for book title
                COLUMN_AUTHOR + " TEXT, " + // Define column for book author
                COLUMN_PAGES + " INTEGER); "; // Define column for number of pages
        db.execSQL(query); // Execute the SQL query
    }

    // Method called when the database needs to be upgraded
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME); // Drop the existing table if it exists
        onCreate(db); // Create a new table
    }


    // ---------------------------------  2. Adding Data into Database.
    void addBook(String title, String author, int pages) {
        SQLiteDatabase db = this.getWritableDatabase(); // Get a writable database instance using 'this' keyword to refer to the current object
        ContentValues cv = new ContentValues(); // Create ContentValues to store data to be inserted into the database

        // Put values into ContentValues object
        cv.put(COLUMN_TITLE, title); // Put the title into ContentValues
        cv.put(COLUMN_AUTHOR, author); // Put the author into ContentValues (should be author instead of title)
        cv.put(COLUMN_PAGES, pages); // Put the pages into ContentValues (should be pages instead of title)

        long result = db.insert(TABLE_NAME, null, cv); // Insert data into the database and get the result

        // Check if insertion was successful
        if (result == -1) {
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show(); // Show a toast message indicating failure if insertion failed
        } else {
            Toast.makeText(context, "Added Successfully!", Toast.LENGTH_SHORT).show(); // Show a toast message indicating success if insertion was successful
        }
    }

    // ---------------------------------  3. Reading Data into Database.
    Cursor readAllData() {
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        if (db != null) {
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }


}
