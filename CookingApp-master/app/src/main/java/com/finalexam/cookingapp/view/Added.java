package com.finalexam.cookingapp.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.finalexam.cookingapp.R;
import com.finalexam.cookingapp.view.activity.HomeActivity;
import com.finalexam.cookingapp.view.activity.LoginActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Added extends AppCompatActivity {
    ImageButton addcake,addfood,adddrink;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
//        addcake = findViewById(R.id.btn_addcake);
//        addcake.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(Added.this,AddRecipe.class));
//            }
//        });
//        addfood = findViewById(R.id.btn_addfood);
//        addfood.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(Added.this,AddRecipe.class));
//            }
//        });
//        adddrink = findViewById(R.id.btn_adddrink);
//        adddrink.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(Added.this,AddRecipe.class));
//            }
//        });
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
                        return true;
                    case R.id.search:
                        startActivity(new Intent(getApplicationContext(), SearchPage.class));
                        overridePendingTransition(0, 0);
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