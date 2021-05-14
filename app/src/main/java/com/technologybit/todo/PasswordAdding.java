package com.technologybit.todo;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class PasswordAdding extends AppCompatActivity {

    EditText tvPassword, tvUser;
    GeneratePassword gn = new GeneratePassword();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_adding);

        // TODO Initialization
        tvPassword = findViewById(R.id.tvPassword);
        tvUser = findViewById(R.id.tvUser);

    }

    public void btnCreatePass(View view) {
        String userInputs = tvUser.getText().toString();
        String passwordInputs = tvPassword.getText().toString();
        if (userInputs.equals("") || passwordInputs.equals("")) {
            Toast.makeText(getBaseContext(), "Please Enter All The Fields",
                    Toast.LENGTH_SHORT).show();
        } else {
            Intent resultIntent = new Intent();
            // TODO Add extras or a data URI to this intent as appropriate.
            resultIntent.putExtra("password", passwordInputs);
            resultIntent.putExtra("user", userInputs);
            setResult(RESULT_OK, resultIntent);
            finish();
        }
    }

    public void btnGenerate(View view) {

        tvPassword.getText().clear();
        String password = gn.generatePassword();
        tvPassword.setText(password, TextView.BufferType.EDITABLE);

    }

}