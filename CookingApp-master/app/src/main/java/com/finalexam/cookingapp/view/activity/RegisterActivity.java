package com.finalexam.cookingapp.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


import com.finalexam.cookingapp.R;
import com.finalexam.cookingapp.viewmodel.NetworkProvider;


public class RegisterActivity extends AppCompatActivity {
    TextView login;
    Button register_btn;
    EditText fullName, email, password;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        login = findViewById(R.id.tv_login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        });

        register_btn = findViewById(R.id.btn_registerR);
        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fullName = findViewById(R.id.etnameR);
                email = findViewById(R.id.etemailR);
                password = findViewById(R.id.etpassR);
                NetworkProvider.self().signUp(fullName.getText().toString(), email.getText().toString(), password.getText().toString());
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        });
    }
}
