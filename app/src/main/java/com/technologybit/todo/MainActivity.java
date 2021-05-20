package com.technologybit.todo;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.technologybit.todo.Category.Helper.CategoryActivity;
import com.technologybit.todo.Password.PasswordActivity;

public class MainActivity extends AppCompatActivity {

    Button btnAddWork, btnPass;
    TextView tvToDo, tvQuote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialising the views and buttons
        btnAddWork = findViewById(R.id.btnAddWork);
        tvToDo = findViewById(R.id.tvToDo);
        tvQuote = findViewById(R.id.tvQuote);
        btnPass = findViewById(R.id.btnPass);

        // Animations
        btnAddWork.setAlpha(0);
        btnAddWork.animate().setDuration(800).alpha(1);

        btnPass.setAlpha(0);
        btnPass.animate().setDuration(800).alpha(1);

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

    public void btnPasswords(View view) {

//        biometricPrompt.authenticate(promptInfo);

        Intent intent = new Intent(MainActivity.this, PasswordActivity.class);
        startActivity(intent);

    }


}