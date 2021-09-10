package com.finalexam.cookingapp.view.activity;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.finalexam.cookingapp.R;
import com.finalexam.cookingapp.database.DatabaseHandler;
import com.finalexam.cookingapp.model.entity.User;
import com.finalexam.cookingapp.view.menu.Nav;
import com.finalexam.cookingapp.viewmodel.CategoryAdapter;

public class AddActivity extends AppCompatActivity {
    private TextView tvHello2;
    private RecyclerView rvCategory;
    private CategoryAdapter categoryAdapter;
    DatabaseHandler databaseHandler;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        databaseHandler = new DatabaseHandler(getApplicationContext());

        User user = databaseHandler.getCurrentAccount();

        if (user != null) {
            tvHello2 = findViewById(R.id.tv_hello2);
            tvHello2.setText("Hello " + user.getFullName());
        }

        rvCategory = findViewById(R.id.rv_category);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rvCategory.setLayoutManager(linearLayoutManager);

        categoryAdapter = new CategoryAdapter(databaseHandler.getAllCategories());
        rvCategory.setAdapter(categoryAdapter);

        Nav nav = new Nav(AddActivity.this);
    }
}