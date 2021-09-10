package com.finalexam.cookingapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.finalexam.cookingapp.view.Fav;
import com.finalexam.cookingapp.view.SearchPage;
import com.finalexam.cookingapp.view.activity.AddActivity;
import com.finalexam.cookingapp.view.activity.LoginActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomePage extends AppCompatActivity {
    ImageButton cake,food,drink,cakeFOC,drinkPT,ava;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
//        ava = findViewById(R.id.ava);
//        ava.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(HomePage.this,Infor.class));
//            }
//        });
//        cake = findViewById(R.id.btn_cake);
//        cake.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(HomePage.this,Cake.class));
//            }
//        });
//        drink = findViewById(R.id.btn_drink);
//        drink.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(HomePage.this,Drink.class));
//            }
//        });

        BottomNavigationView nav = findViewById(R.id.bottomnav);
        nav.setSelectedItemId(R.id.home);
        nav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        return true;
                    case R.id.add:
                        startActivity(new Intent(getApplicationContext(), AddActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.fav:
                        startActivity(new Intent(getApplicationContext(), Fav.class));
                        overridePendingTransition(0, 0);
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
