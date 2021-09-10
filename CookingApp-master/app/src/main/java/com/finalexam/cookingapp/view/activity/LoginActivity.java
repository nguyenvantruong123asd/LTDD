package com.finalexam.cookingapp.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.finalexam.cookingapp.R;
import com.finalexam.cookingapp.database.DatabaseHandler;
import com.finalexam.cookingapp.viewmodel.NetworkProvider;

public class LoginActivity extends AppCompatActivity {
    TextView register;
    Button login;
    EditText email, password;
    DatabaseHandler databaseHandler;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        databaseHandler = new DatabaseHandler(getApplicationContext());

        setContentView(R.layout.activity_login);
        register = findViewById(R.id.tv_register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });
        login = findViewById(R.id.btn_loginL);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = findViewById(R.id.etemailL);
                password = findViewById(R.id.etpassL);

                NetworkProvider.self().login(email.getText().toString(), password.getText().toString(), LoginActivity.this);
//                startActivity(new Intent(Login.this,HomePage.class));
            }
        });
    }
}
