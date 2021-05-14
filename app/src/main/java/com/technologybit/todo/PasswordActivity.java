package com.technologybit.todo;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;

import com.baoyz.swipemenulistview.SwipeMenuListView;

import java.util.ArrayList;

public class PasswordActivity extends AppCompatActivity {

    SwipeMenuListView passListView;
    String returnPassword,returnUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);

        passListView = findViewById(R.id.passListView);

        addToSwipeListView();

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
        }

    }

    public void addToSwipeListView() {

        ArrayList<String> userPass = new ArrayList<>();
        userPass.add(returnUser + " | " + returnPassword);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, userPass);
        passListView.setAdapter(arrayAdapter);

    }

}