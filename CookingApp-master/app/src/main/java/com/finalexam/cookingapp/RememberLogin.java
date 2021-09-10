package com.finalexam.cookingapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.finalexam.cookingapp.view.activity.LoginActivity;
import com.finalexam.cookingapp.view.activity.RegisterActivity;

public class RememberLogin extends AppCompatActivity {
    TextView acc,regis;
    ImageButton people1,people2;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rememberlogin);
        regis = findViewById(R.id.tv_registerr);
        regis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RememberLogin.this,
                                         RegisterActivity.class));
            }
        });
        acc = findViewById(R.id.tv_another);
        acc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RememberLogin.this, LoginActivity.class));
            }
        });
        people1 = findViewById(R.id.people1);
        people1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RememberLogin.this,Account.class));
                overridePendingTransition(R.anim.slideinleft,R.anim.slideoutleft);
            }
        });
        people2 = findViewById(R.id.people2);
        people2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RememberLogin.this,HomePage.class));
            }
        });
    }
}
