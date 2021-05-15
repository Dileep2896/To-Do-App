package com.technologybit.todo;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import java.util.ArrayList;
import java.util.List;

public class PasswordActivity extends AppCompatActivity {

    SwipeMenuListView passListView;
    String returnPassword,returnUser;
    DatabasePasswordHelper db;
    List<PasswordManagerList> ps;
    MyCustomListAdapter arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);

        // Initializing
        passListView = findViewById(R.id.passListView);
        db = new DatabasePasswordHelper(this);
        ps = new ArrayList<>();

        // create a arraylist

        retrieveData();

    }

    public void btnBackPass(View view) {
        finish();
    }

    // TODO Go To Password Creating Activity
    public void addPass(View view) {
        startActivityForResult(new Intent(getApplicationContext(), PasswordAdding.class), 0);
    }

    // TODO Get back the password generated from the password creating activity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_FIRST_USER && data != null && requestCode == 0) {
            // TODO Extract the data returned from the child Activity.
            returnPassword = data.getStringExtra("password");
            returnUser = data.getStringExtra("user");
            Log.i("User, Password", returnUser + " | " + returnPassword);
            storeData(returnUser, returnPassword);
            retrieveData();
        }

    }


    // ------------------------------ WORKING WITH DATABASE ------------------------------------ //

    // storing data to database password table
    public void storeData(String user, String password) {
        if (!user.equals("") && !password.equals("") &&
                db.insertDataPassword(user, password)) {
            ps.clear();
            Log.i("User & Password", user + " | " + password);
        } else {
            Log.i("Data Added", "NOT!!");
        }
    }

    public void retrieveData() {

        Cursor cursor = db.viewPassData();
        if(cursor.getCount() == 0) {
            Toast.makeText(getBaseContext(), "No Data To Show",
                    Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                String username = cursor.getString(1);
                String password = cursor.getString(2);

                ps.add(new PasswordManagerList(username, password));

            }
        }

        arrayAdapter = new MyCustomListAdapter(this,
                R.layout.my_list_view, ps);

        passListView.setAdapter(arrayAdapter);
        passListView.setMenuCreator(creator);

        passListView.setOnMenuItemClickListener((position, menu, index) -> {
            PasswordManagerList item = arrayAdapter.getItem(position);
            if (index == 0) {
                String user = item.getUsername();
                String password = item.getPassword();
                Log.i("Item", password + "  ====  " + user);
                deleteData(password, user);
            }
            // false : close the menu; true : not close the menu
            return false;
        });

    }

    // Deleting Data From Database
    public void deleteData(String pass, String user) {
        Cursor cursor = db.getItemID(pass, user);
        int itemID = -1;
        while (cursor.moveToNext()) {
            itemID = cursor.getInt(0);
        }
        Log.i("ITEM ID", String.valueOf(itemID));
        if (itemID > -1) {
            Log.i("Got ID: ", Integer.toString(itemID));
            db.deleteData(itemID, pass, user);
            ps.clear();
            retrieveData();
        } else {
            Log.i("Got ID: ", "NOT GOT ID");
        }
    }

    // ------------------------------ Set Creator For The ListView --------------------------------- //

    SwipeMenuCreator creator = menu -> {
        // create "delete" item
        SwipeMenuItem deleteItem = new SwipeMenuItem(
                getApplicationContext());
        // set item background
        deleteItem.setBackground(R.drawable.custom_listview_bg);
        // set item width
        deleteItem.setWidth(200);
        // set a icon
        deleteItem.setIcon(R.drawable.ic_baseline_delete_24);
        // add to menu
        menu.addMenuItem(deleteItem);
    };


}