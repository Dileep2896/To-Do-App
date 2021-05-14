package com.technologybit.todo;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;

import java.util.ArrayList;

public class CategoryActivity extends AppCompatActivity implements DialogAdd.DialogAddListener {

    SwipeMenuListView listViewCategory;
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

    }

// ------------------------------  When Info Button Is Clicked --------------------------------- //

    public void btnInfo(View view) {
        new AlertDialog.Builder(this)
                .setTitle("\t How To Use The App?")
                .setMessage("\t 1) ' + ' - This button is used to add an" +
                        "\n\t item to your remainder. \n\n" +
                        "\t 2) You can swipe right the items to" +
                        "\n\t delete the item from the lists. \n\n" +
                        "\t 3) ' <- ' Can be used to go back to" +
                        "\n\t the home page. \n\n\n" +
                        "\t Enjoy The App And Please Leave A" +
                        "\n\t Review To Improve User Experience.")
                .setPositiveButton("OK", (dialogInterface, i) -> {
                })
                .show();
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

        arrayAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, categoryList) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent){
                View rView = super.getView(position,convertView,parent);
                TextView tv = rView.findViewById(android.R.id.text1);
                rView = customizeListview(rView, tv, position);
                return rView;
            }
        };

        listViewCategory.setMenuCreator(creator);
        listViewCategory.setAdapter(arrayAdapter);

        listViewCategory.setOnMenuItemClickListener((position, menu, index) -> {
            String cName = arrayAdapter.getItem(position);
            if (index == 0) {
                deleteData(cName);
            }
            // false : close the menu; true : not close the menu
            return false;
        });

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

// -------------------------------- Customize List View Items --------------------------------- //

    public View customizeListview(View view, TextView tv, int position) {
        //Define your height here.
        view.setPadding(100, 50, 0, 50);
        tv.setTypeface(Typeface.SANS_SERIF, Typeface.BOLD);
        tv.setTextSize(20);
        tv.setTextColor(Color.rgb(56, 62, 86));
        if (position % 2 == 0) {
            view.setBackgroundColor(Color.rgb(228, 251, 255));
            //            alternativeColor = false;
        } else {
            view.setBackgroundColor(Color.rgb(159, 216, 223));
            //            alternativeColor = true;
        }
        return view;
    }

// ------------------------------ Set Creator For The ListView --------------------------------- //

    SwipeMenuCreator creator = menu -> {
        // create "delete" item
        SwipeMenuItem deleteItem = new SwipeMenuItem(
                getApplicationContext());
        // set item background
        deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9,
                0x3F, 0x25)));
        // set item width
        deleteItem.setWidth(200);
        // set a icon
        deleteItem.setIcon(R.drawable.ic_baseline_delete_24);
        // add to menu
        menu.addMenuItem(deleteItem);
    };

}