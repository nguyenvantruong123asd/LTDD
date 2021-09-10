package com.finalexam.cookingapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.finalexam.cookingapp.model.entity.Category;
import com.finalexam.cookingapp.model.entity.Ingredient;
import com.finalexam.cookingapp.model.entity.User;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "mobile";
    private static final int DATABASE_VERSION = 3;

    private static final String USER_TABLE_NAME = "user";
    private static final String USER_COLUMN_ID = "id";
    private static final String USER_COLUMN_FULL_NAME = "full_name";
    private static final String USER_COLUMN_EMAIL = "email";
    private static final String USER_COLUMN_CURRENT_ACCOUNT = "current_account";

    private static final String CATEGORY_TABLE_NAME = "category";
    private static final String CATEGORY_COLUMN_ID = "id";
    private static final String CATEGORY_COLUMN_IMAGE_ID = "image_id";
    private static final String CATEGORY_COLUMN_NAME = "name";

    private static final String INGREDIENT_TABLE_NAME = "ingredient";
    private static final String INGREDIENT_COLUMN_ID = "id";
    private static final String INGREDIENT_COLUMN_NAME = "name";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createUserTableCommand = String.format("CREATE TABLE %s(%s INTEGER PRIMARY KEY, %s TEXT, %s TEXT, %s INTEGER)", USER_TABLE_NAME, USER_COLUMN_ID, USER_COLUMN_FULL_NAME, USER_COLUMN_EMAIL, USER_COLUMN_CURRENT_ACCOUNT);
        String createCategoryTableCommand = String.format("CREATE TABLE %s(%s INTEGER PRIMARY KEY, %s TEXT, %s TEXT)", CATEGORY_TABLE_NAME, CATEGORY_COLUMN_ID, CATEGORY_COLUMN_IMAGE_ID, CATEGORY_COLUMN_NAME);
        String createIngredientTableCommand = String.format("CREATE TABLE %s(%s INTEGER PRIMARY KEY, %s TEXT)", INGREDIENT_TABLE_NAME, INGREDIENT_COLUMN_ID, INGREDIENT_COLUMN_NAME);

        db.execSQL(createCategoryTableCommand);
        db.execSQL(createUserTableCommand);
        db.execSQL(createIngredientTableCommand);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String dropUserTable = String.format("DROP TABLE IF EXISTS %s", USER_TABLE_NAME);
        String dropCategoryTable = String.format("DROP TABLE IF EXISTS %s", CATEGORY_TABLE_NAME);
        String dropIngredientTable = String.format("DROP TABLE IF EXISTS %s", INGREDIENT_TABLE_NAME);

        db.execSQL(dropUserTable);
        db.execSQL(dropCategoryTable);

        onCreate(db);
    }

    public void addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(USER_COLUMN_ID, user.getId());
        values.put(USER_COLUMN_FULL_NAME, user.getFullName());
        values.put(USER_COLUMN_EMAIL, user.getEmail());
        values.put(USER_COLUMN_CURRENT_ACCOUNT, user.getCurrentAccount());

        db.insert(USER_TABLE_NAME, null, values);
    }

    public User getCurrentAccount() {
        SQLiteDatabase db = this.getReadableDatabase();

        try {
            Cursor cursor = db.query(USER_TABLE_NAME, null, USER_COLUMN_CURRENT_ACCOUNT + " = ?", new String[]{String.valueOf(1)}, null, null, null);

            if (cursor != null)
                cursor.moveToFirst();

            User user = new User(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getInt(3));
            return user;
        } catch (CursorIndexOutOfBoundsException ex) {
            return null;
        }
    }

    public void logout() {
        SQLiteDatabase db = this.getWritableDatabase();

        String deleteQuery = "DELETE FROM " + USER_TABLE_NAME + " WHERE " + USER_COLUMN_CURRENT_ACCOUNT + "=1;";
        db.execSQL(deleteQuery);
    }

    public void addCategory(Category category) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(CATEGORY_COLUMN_ID, category.getId());
        values.put(CATEGORY_COLUMN_IMAGE_ID, category.getImageID());
        values.put(CATEGORY_COLUMN_NAME, category.getCategoryName());

        db.insert(CATEGORY_TABLE_NAME, null, values);
    }

    public List<Category> getAllCategories() {
        SQLiteDatabase db = this.getReadableDatabase();

        List<Category> categories = new ArrayList<>();
        try {
            String getAllCategoriesCommand = "SELECT * FROM " + CATEGORY_TABLE_NAME;
            Cursor cursor = db.rawQuery(getAllCategoriesCommand, null);


            if (cursor.moveToFirst()) {
                while(!cursor.isAfterLast()) {
                    Category category = new Category(cursor.getInt(0), cursor.getString(1), cursor.getString(2));
                    categories.add(category);

                    cursor.moveToNext();
                }
            }

            return categories;

        } catch (CursorIndexOutOfBoundsException ex) {
        }
        return categories;
    }

    public Category getCategory(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        try {
            Cursor cursor = db.query(CATEGORY_TABLE_NAME, null, CATEGORY_COLUMN_ID + " = ?", new String[]{String.valueOf(id)}, null, null, null);

            if (cursor != null)
                cursor.moveToFirst();

            Category category = new Category(cursor.getInt(0), cursor.getString(1), cursor.getString(2));
            return category;
        } catch (CursorIndexOutOfBoundsException ex) {
            return null;
        }
    }

    public void addIngredient(Ingredient ingredient) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(INGREDIENT_COLUMN_ID, ingredient.getId());
        values.put(INGREDIENT_COLUMN_NAME, ingredient.getIngredientName());

        db.insert(INGREDIENT_TABLE_NAME, null, values);
    }

    public List<Ingredient> getAllIngredients() {
        SQLiteDatabase db = this.getReadableDatabase();

        List<Ingredient> ingredients = new ArrayList<>();
        try {
            String getAllIngredientsCommand = "SELECT * FROM " + INGREDIENT_TABLE_NAME;
            Cursor cursor = db.rawQuery(getAllIngredientsCommand, null);


            if (cursor.moveToFirst()) {
                while(!cursor.isAfterLast()) {
                    Ingredient ingredient = new Ingredient(cursor.getInt(0), cursor.getString(1));
                    ingredients.add(ingredient);

                    cursor.moveToNext();
                }
            }

            return ingredients;

        } catch (CursorIndexOutOfBoundsException ex) {
        }
        return ingredients;
    }

    public Ingredient getIngredient(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        try {
            Cursor cursor = db.query(INGREDIENT_TABLE_NAME, null, INGREDIENT_COLUMN_ID + " = ?", new String[]{String.valueOf(id)}, null, null, null);

            if (cursor != null)
                cursor.moveToFirst();

            Ingredient ingredient = new Ingredient(cursor.getInt(0), cursor.getString(1));
            return ingredient;
        } catch (CursorIndexOutOfBoundsException ex) {
            return null;
        }
    }
}
