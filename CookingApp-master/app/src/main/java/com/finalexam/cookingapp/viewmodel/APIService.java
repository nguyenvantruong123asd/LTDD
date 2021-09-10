package com.finalexam.cookingapp.viewmodel;

import com.finalexam.cookingapp.model.entity.Category;
import com.finalexam.cookingapp.model.entity.Ingredient;
import com.finalexam.cookingapp.model.request.CreateFoodRequest;
import com.finalexam.cookingapp.model.response.CreateFoodResponse;
import com.finalexam.cookingapp.model.response.LoginResponse;
import com.finalexam.cookingapp.model.request.SignUpRequest;
import com.finalexam.cookingapp.model.response.SignUpResponse;
import com.finalexam.cookingapp.model.response.UploadImageResponse;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface APIService {
    String prefixUrl = "/mobile_api/";

    @POST(prefixUrl + "users/sign-up")
    Call<SignUpResponse> signUp(@Body SignUpRequest signUpRequest);

    @POST(prefixUrl + "users/log-in")
    Call<LoginResponse> login(@Query("email") String email, @Query("password") String password);

    @GET(prefixUrl + "categories")
    Call<List<Category>> getAllCategories();

    @GET(prefixUrl + "ingredients")
    Call<List<Ingredient>> getAllIngredient();

    @Multipart
    @POST(prefixUrl + "image")
    Call<UploadImageResponse> uploadImage(@Part MultipartBody.Part coverImage);

    @POST(prefixUrl + "food")
    Call<CreateFoodResponse> createFood(@Body CreateFoodRequest request);
}
