package com.finalexam.cookingapp.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.finalexam.cookingapp.R;
import com.finalexam.cookingapp.view.activity.HomeActivity;
import com.finalexam.cookingapp.view.activity.LoginActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class SearchPage extends AppCompatActivity {
    ListView listG, listR;
    ArrayList<String> arrayG = new ArrayList<>();
    ArrayList<String> arrayR = new ArrayList<>();
    ArrayAdapter<String> adapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchpage);
        listG = findViewById(R.id.listgredient);
        arrayG.add("Chicken");
        arrayG.add("Fish");
        arrayG.add("Beef");
        arrayG.add("Pork");
        arrayG.add("Celery");
        arrayG.add("Onion");
        arrayG.add("Garlic");
        arrayG.add("Egg");
        adapter = new ArrayAdapter<>(SearchPage.this, android.R.layout.simple_list_item_1, arrayG);
        listG.setAdapter(adapter);
        listR = findViewById(R.id.listrecipe);
        arrayR.add("Stir Fry Beef with Celery | Quick & Easy Celery Beef Stir Fry Recipe");
        arrayR.add("BEEF with CELERY ♡ STIR FRY recipe");
        arrayR.add("BETTER THAN TAKEOUT AND EASY - Pork Stir Fry with Celery");
        arrayR.add("5 Minutes EASY Egg Fried Rice");

        adapter = new ArrayAdapter<>(SearchPage.this, android.R.layout.simple_list_item_1, arrayR);
        listR.setAdapter(adapter);
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
                        startActivity(new Intent(getApplicationContext(), Added.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.search:
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
