package com.finalexam.cookingapp.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.finalexam.cookingapp.R;

public class Spaghetti extends AppCompatActivity {
    ImageButton back;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spagetti);
        back = findViewById(R.id.btn_backS);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Spaghetti.this,Food.class));
            }
        });
    }
}