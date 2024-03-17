package com.example.SQLiteDatabase;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.dqlite.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView; // RecyclerView to display the data
    FloatingActionButton add_button; // FloatingActionButton to add new data
    MyDatabaseHelper myDB; // Instance of the database helper class
    ArrayList<String>  book_id, book_title, book_author, book_pages; // ArrayLists to store data retrieved from the database
    CustomAdapter customAdapter;    // Calling CustomAdapter class

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize RecyclerView and FloatingActionButton
        recyclerView = findViewById(R.id.recyclerView);
        add_button = findViewById(R.id.addButton);

        // Set click listener for the FloatingActionButton to navigate to AddActivity
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddActivity.class);
                startActivity(intent);
            }
        });

        // Initialize database helper and ArrayLists
        myDB = new MyDatabaseHelper(MainActivity.this);
        book_id = new ArrayList<>();
        book_author = new ArrayList<>();
        book_title = new ArrayList<>();
        book_pages = new ArrayList<>();

        // Call method to retrieve data from the database and store it in ArrayLists
        storeDataInArray();

        customAdapter = new CustomAdapter(MainActivity.this, book_id,book_author,book_title,book_pages);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));

    }


    // Method to retrieve data from the database and store it in ArrayLists
    void storeDataInArray(){
        // Read all data from the database using Cursor
        Cursor cursor = myDB.readAllData();
        if (cursor.getCount() == 0){
            // Show a toast message if there is no data in the database
            Toast.makeText(this, "There Is No Data", Toast.LENGTH_SHORT).show();
        }else {
            // Iterate through the Cursor and add data to the ArrayLists
            while (cursor.moveToNext()){
                book_id.add(cursor.getString(0)); // Add book ID to the ArrayList
                book_author.add(cursor.getString(1)); // Add book author to the ArrayList
                book_title.add(cursor.getString(2)); // Add book title to the ArrayList
                book_pages.add(cursor.getString(3)); // Add book pages to the ArrayList
            }
        }
    }
}
