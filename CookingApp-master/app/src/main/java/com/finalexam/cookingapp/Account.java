package com.finalexam.cookingapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.finalexam.cookingapp.view.activity.LoginActivity;
import com.finalexam.cookingapp.view.activity.RegisterActivity;

public class Account extends AppCompatActivity {
    ImageButton people1, back;
    EditText pass;
    TextView acc, regis;
    Button login;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        back = findViewById(R.id.btn_backA);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Account.this, RememberLogin.class));
                overridePendingTransition(R.anim.slideinleft, R.anim.slideoutleft);
            }
        });
        regis = findViewById(R.id.tv_registerr);
        regis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Account.this, RegisterActivity.class));
            }
        });
        acc = findViewById(R.id.tv_another);
        acc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Account.this, LoginActivity.class));
            }
        });
        people1 = findViewById(R.id.people1);
        Animation animFade = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate);
        people1.startAnimation(animFade);
        pass = findViewById(R.id.etpassA);
        Animation animFade1 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate);
        pass.startAnimation(animFade);
        login = findViewById(R.id.btn_loginA);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Account.this, HomePage.class));
            }
        });
    }
}
