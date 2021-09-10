package com.finalexam.cookingapp.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.finalexam.cookingapp.R;
import com.finalexam.cookingapp.view.activity.HomeActivity;

public class Food extends AppCompatActivity {
    ImageButton food,back;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);
        food = findViewById(R.id.btn_food6);
        food.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Food.this,Spaghetti.class));
            }
        });
        back = findViewById(R.id.btn_backF);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Food.this, HomeActivity.class));
            }
        });
    }
}