package com.finalexam.cookingapp.view.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.finalexam.cookingapp.GlobalStorage;
import com.finalexam.cookingapp.R;
import com.finalexam.cookingapp.model.global.storage.DetailIngredient;
import com.finalexam.cookingapp.model.request.CreateFoodRequest;
import com.finalexam.cookingapp.utils.RealPathUtil;
import com.finalexam.cookingapp.view.Added;
import com.finalexam.cookingapp.viewmodel.NetworkProvider;
import com.finalexam.cookingapp.viewmodel.ViewPagerAdapter;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class AddRecipeActivity extends AppCompatActivity {
    private final static int REQUEST_CODE = 10;

    ImageButton back;
    TabLayout tlAddRecipe;
    ViewPager vpAddRecipe;
    FloatingActionButton fabImage;
    ImageView ivCover;
    Button btnSave;
    EditText etFoodName;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int categoryID = getIntent().getIntExtra("categoryID", 0);

        setContentView(R.layout.activity_addrecipe);

        tlAddRecipe = findViewById(R.id.tl_add_recipe);
        vpAddRecipe = findViewById(R.id.vp_add_recipe);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(
                getSupportFragmentManager(),
                FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
        );
        vpAddRecipe.setAdapter(viewPagerAdapter);

        tlAddRecipe.setupWithViewPager(vpAddRecipe);

        back = findViewById(R.id.btn_backaddcake);
        back.setOnClickListener(v -> startActivity(
                new Intent(
                        AddRecipeActivity.this,
                        AddActivity.class
                )));

        ivCover = findViewById(R.id.iv_cover);

        fabImage = findViewById(R.id.fab_image);
        fabImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                    return;
                }

                // Check permission, if not, request
                if (checkSelfPermission(
                        Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    String[] permission = {Manifest.permission.READ_EXTERNAL_STORAGE};
                    requestPermissions(permission, REQUEST_CODE);
                }
                ImagePicker.Companion.with(AddRecipeActivity.this).start();

            }
        });

        etFoodName = findViewById(R.id.et_food_name);

        btnSave = findViewById(R.id.btn_save);
        btnSave.setOnClickListener(view -> {
            EditText etPreparation = findViewById(R.id.et_preparation);
            String preparation = etPreparation.getText().toString();

            String foodName = etFoodName.getText().toString();
            System.out.println(foodName);

            CreateFoodRequest createFoodRequest = new CreateFoodRequest(
                    categoryID, 0,
                    GlobalStorage.self().getRecipeData().getDetailIngredients(),
                    foodName, 0, 0, 0, preparation, null
            );
            NetworkProvider.self().uploadImage(createFoodRequest, AddRecipeActivity.this);
        });
    }

    @Override
    protected void onActivityResult(
            int requestCode, int resultCode,
            @Nullable Intent data
    ) {
        super.onActivityResult(requestCode, resultCode, data);

        Uri uri = data.getData();
        String imageUrl = RealPathUtil.getRealPath(
                getApplicationContext(),
                uri
        );
        Log.i("Image url", imageUrl);
        GlobalStorage.self().getRecipeData().setCoverImageUrl(imageUrl);
        ivCover.setImageURI(uri);
    }
}
