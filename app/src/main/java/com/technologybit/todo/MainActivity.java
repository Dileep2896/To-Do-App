package com.technologybit.todo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    Button btnAddWork;
    TextView tvToDo, tvQuote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialising the views and buttons
        btnAddWork = findViewById(R.id.btnAddWork);
        tvToDo = findViewById(R.id.tvToDo);
        tvQuote = findViewById(R.id.tvQuote);

        // Enabling Full Screen And Adding Action Bar.
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                             WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Objects.requireNonNull(getSupportActionBar()).hide();

        // Animations
        btnAddWork.setAlpha(0);
        btnAddWork.animate().setDuration(800).alpha(1);

        tvToDo.setAlpha(0);
        tvToDo.animate().setDuration(800).alpha(1);

        tvQuote.setAlpha(0);
        tvQuote.animate().setDuration(1000).alpha(1);

    }

    // Going to next activity
    public void WorkBtnClicked(View view) {

        // Creating intent to move to category activity
        Intent intent = new Intent(MainActivity.this, CategoryActivity.class);
        startActivity(intent);

    }
}