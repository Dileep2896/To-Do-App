package com.technologybit.todo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
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

        listViewCategory = findViewById(R.id.listViewCategory);
        db = new DatabaseHelper(this);

        categoryList = new ArrayList<String>();
        retrieveData();

        listViewCategory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String cName = adapterView.getItemAtPosition(i).toString();

                deleteData(cName);
            }
        });

    }

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

    public void storeData(String Category) {
        if (!Category.equals("") && db.insertData(Category)) {
            Log.i("Category", "DATA ADDED");
            categoryList.clear();
            retrieveData();
        } else {
            Log.i("Category", "DATA NOT ADDED");
        }
    }

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

        arrayAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, categoryList);
        listViewCategory.setAdapter(arrayAdapter);

    }

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


}