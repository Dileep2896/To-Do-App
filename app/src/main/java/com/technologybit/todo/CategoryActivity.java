package com.technologybit.todo;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class CategoryActivity extends AppCompatActivity implements DialogAdd.DialogAddListener {

    ListView listViewCategory;
    ArrayList<String> categoryList;
    ArrayAdapter<String> arrayAdapter;
    DatabaseHelper db;

    @SuppressLint("CommitPrefEdits")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        // Initialising
        listViewCategory = findViewById(R.id.listViewCategory);
        db = new DatabaseHelper(this);

        // Created a new arraylist to display data
        categoryList = new ArrayList<>();
        // Calling read data from the database
        retrieveData();

        // List View Item Listener To Delete Data From The List View
        listViewCategory.setOnItemClickListener((adapterView, view, i, l) -> {
            String cName = adapterView.getItemAtPosition(i).toString();
            deleteData(cName);
        });

    }

// ------------------------------- WORKING WITH DIALOG BOX ------------------------------------- //

    // Opening Dialog Box When Add is clicked
    public void addButtonClicked(View view) {
        openDialog();
    }
    // Back Button Function
    public void goBackClicked(View view) {
        finish();
    }

    // Opening Dialog Box From DialogAdd Class
    public void openDialog() {
        DialogAdd dialogAdd = new DialogAdd();
        dialogAdd.show(getSupportFragmentManager(), "Add Category");
    }

    // Getting Text From The Dialog Box
    @Override
    public void addTexts(String Category) {
        storeData(Category);
    }

// ----------------------------- END WORKING WITH DIALOG BOX ----------------------------------- //

// --------------------------------- WORKING WITH DATABASES ------------------------------------ //

    // Storing Data In Database
    public void storeData(String Category) {
        if (!Category.equals("") && db.insertData(Category)) {
            Log.i("Category", "DATA ADDED");
            categoryList.clear();
            retrieveData();
        } else {
            Log.i("Category", "DATA NOT ADDED");
        }
    }

    // Reading Data From Database
    public void retrieveData() {
        Cursor cursor = db.viewData();

        if(cursor.getCount() == 0) {
            Toast.makeText(CategoryActivity.this, "No Data To Show",
                    Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                categoryList.add(cursor.getString(1));
            }
        }

        arrayAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, categoryList);
        listViewCategory.setAdapter(arrayAdapter);

    }

    // Deleting Data From Database
    public void deleteData(String name) {
        Cursor cursor = db.getItemID(name);
        int itemID = -1;
        while (cursor.moveToNext()) {
            itemID = cursor.getInt(0);
        }
        if (itemID > -1) {
            Log.i("Got ID: ", Integer.toString(itemID));
            db.deleteData(itemID, name);
            categoryList.clear();
            retrieveData();
        } else {
            Log.i("Got ID: ", "NOT GOT ID");
        }
    }

// -------------------------------- END WORKING WITH DATABASES -------------------------------- //

}