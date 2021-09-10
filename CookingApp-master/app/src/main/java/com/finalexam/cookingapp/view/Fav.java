package com.finalexam.cookingapp.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.finalexam.cookingapp.R;
import com.finalexam.cookingapp.view.activity.AddActivity;
import com.finalexam.cookingapp.view.activity.HomeActivity;
import com.finalexam.cookingapp.view.activity.LoginActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Fav extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fav);
        BottomNavigationView nav = findViewById(R.id.bottomnav);
        nav.setSelectedItemId(R.id.home);
        nav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.add:
                        startActivity(new Intent(getApplicationContext(), AddActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.search:
                        startActivity(new Intent(getApplicationContext(), SearchPage.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.fav:
                        return true;
                    case R.id.out:
                        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                }

                return false;
            }
        });
    }
}
