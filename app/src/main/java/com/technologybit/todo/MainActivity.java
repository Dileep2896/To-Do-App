package com.technologybit.todo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import java.util.concurrent.Executor;

public class MainActivity extends AppCompatActivity {

    Button btnAddWork, btnPass;
    TextView tvToDo, tvQuote;

    private BiometricPrompt biometricPrompt;
    private BiometricPrompt.PromptInfo promptInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Authentication();

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

    public void Authentication() {
        Executor executor = ContextCompat.getMainExecutor(this);
        biometricPrompt = new BiometricPrompt(MainActivity.this,
                executor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode,
                                              @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
                Toast.makeText(getApplicationContext(),
                        "Authentication error: " + errString, Toast.LENGTH_SHORT)
                        .show();
            }

            @Override
            public void onAuthenticationSucceeded(
                    @NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                Toast.makeText(getApplicationContext(),
                        "Authentication succeeded!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, PasswordActivity.class);
                startActivity(intent);
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                Toast.makeText(getApplicationContext(), "Authentication failed",
                        Toast.LENGTH_SHORT)
                        .show();
            }
        });

        promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle("Biometric login for my app")
                .setSubtitle("Log in using your biometric credential")
                .setNegativeButtonText("Use account password")
                .build();

    }

}