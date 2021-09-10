package com.finalexam.cookingapp.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.finalexam.cookingapp.R;
import com.finalexam.cookingapp.view.activity.HomeActivity;

public class Cake extends AppCompatActivity {
    ImageButton cake1,back;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cake);
        cake1 = findViewById(R.id.btn_cake1);
        cake1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Cake.this,FreshOrangeCake.class));
            }
        });
        back = findViewById(R.id.btn_backC);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(Cake.this, HomeActivity.class));
            }
        });
    }
}