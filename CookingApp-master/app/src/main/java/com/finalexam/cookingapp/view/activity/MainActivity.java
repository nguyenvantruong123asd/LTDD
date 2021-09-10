package com.finalexam.cookingapp.view.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.finalexam.cookingapp.R;
import com.finalexam.cookingapp.database.DatabaseHandler;
import com.finalexam.cookingapp.model.entity.User;

public class MainActivity extends AppCompatActivity {
    Button login,register;
    DatabaseHandler databaseHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        databaseHandler = new DatabaseHandler(getApplicationContext());

        User user = databaseHandler.getCurrentAccount();
        if (user != null)
            startActivity(new Intent(MainActivity.this, HomeActivity.class));

        setContentView(R.layout.activity_main);
        login = findViewById(R.id.btn_login);
        register = findViewById(R.id.btn_registerR);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, RegisterActivity.class));
            }
        });
    }
}
