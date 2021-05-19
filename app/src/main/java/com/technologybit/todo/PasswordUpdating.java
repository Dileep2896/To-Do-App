package com.technologybit.todo;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class PasswordUpdating extends AppCompatActivity {

    EditText tvUser, tvPassword;
    String user, password, encryptPassword;
    DatabasePasswordHelper db;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_updating);

        tvUser = findViewById(R.id.tvUser);
        tvPassword = findViewById(R.id.tvPassword);

        tvPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());

        setAndGetData();

    }

    public void btnUpdatePass(View view) {

        Log.i("Encrypted Password", encryptPassword);

        db = new DatabasePasswordHelper(this);
        Cursor cursor = db.getItemID(password, user);
        int itemID = -1;
        while (cursor.moveToNext()) {
            itemID = cursor.getInt(0);
        }

        boolean isUpdate = db.updateData(Integer.toString(itemID), tvUser.getText().toString());

        if (isUpdate) {
            finish();
        } else {
            Toast.makeText(this, "Not Updated", Toast.LENGTH_SHORT).show();
        }

    }

    public void setAndGetData() {

        Intent intent = getIntent();

        user = intent.getStringExtra("User");
        password = intent.getStringExtra("EncryptPassword");
        encryptPassword = intent.getStringExtra("Password");

        tvUser.setText(user);
        tvPassword.setText(encryptPassword);
        tvPassword.setClickable(false);
        tvPassword.setEnabled(false);
        tvPassword.setAlpha((float) 0.5);

    }

    public void btnBank(View view) {

        finish();

    }
}