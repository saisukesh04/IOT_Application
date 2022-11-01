package com.iot.iotapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.snackbar.Snackbar;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.emailEditText) EditText emailEditText;
    @BindView(R.id.passwordEditText) EditText passwordEditText;
    @BindView(R.id.loginButton) Button loginButton;
    @BindView(R.id.sign_up_btn) Button signUpBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);

        loginButton.setOnClickListener(view -> {
            String email = emailEditText.getText().toString();
            String pass = passwordEditText.getText().toString();

            if (email.isEmpty())
                Snackbar.make(view, "Enter valid email!", Snackbar.LENGTH_LONG).show();
            else if (pass.isEmpty())
                Snackbar.make(view, "Password is empty!", Snackbar.LENGTH_LONG).show();
            else {
                if (email.equals("sukesh@gmail.com") && pass.equals("1234")) {
                    Intent intent = new Intent(this, DisplayActivity.class);
                    startActivity(intent);
                    finish();
                } else
                    Snackbar.make(view, "Email and password do not match", Snackbar.LENGTH_LONG).show();
            }
        });

        signUpBtn.setOnClickListener(v -> Snackbar.make(v, "Coming Soon!", Snackbar.LENGTH_LONG).show());
    }
}