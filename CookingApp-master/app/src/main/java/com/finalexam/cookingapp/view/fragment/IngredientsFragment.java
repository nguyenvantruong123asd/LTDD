package com.finalexam.cookingapp.view.fragment;

import android.app.Dialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import com.finalexam.cookingapp.GlobalStorage;
import com.finalexam.cookingapp.R;
import com.finalexam.cookingapp.database.DatabaseHandler;
import com.finalexam.cookingapp.model.global.storage.DetailIngredient;
import com.finalexam.cookingapp.model.entity.Ingredient;
import com.finalexam.cookingapp.viewmodel.NetworkProvider;

import java.util.List;

public class IngredientsFragment extends Fragment {

    private Button btnAddIngredient;
    private ListView lvIngredients;
    private DatabaseHandler databaseHandler;

    public IngredientsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ingredients, container, false);

        GlobalStorage globalStorage = (GlobalStorage) view.getContext().getApplicationContext();

        NetworkProvider.self().getAllIngredients(view.getContext());
        databaseHandler = new DatabaseHandler(view.getContext());
        List<Ingredient> ingredients = databaseHandler.getAllIngredients();

        lvIngredients = view.findViewById(R.id.lv_frg_ingredients);

        ArrayAdapter<String> addedDetailIngredientsAdapter = new ArrayAdapter(view.getContext(),
                R.layout.item_lv_ingredients_added,
                globalStorage.getRecipeData().getDetailIngredientsContent());
        lvIngredients.setAdapter(addedDetailIngredientsAdapter);

        btnAddIngredient = view.findViewById(R.id.btn_add_ingredient);
        btnAddIngredient.setOnClickListener(v -> {
            Dialog dialog = new Dialog(view.getContext());
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.dialog_add_item_ingredient);

            Spinner spIngredients = dialog.findViewById(R.id.sp_ingredients_dialog);
            ArrayAdapter<Ingredient> listAvailableIngredientAdapter = new ArrayAdapter(
                    dialog.getContext(), R.layout.item_ingredient, ingredients);

            listAvailableIngredientAdapter.setDropDownViewResource(R.layout.item_ingredient);
            spIngredients.setAdapter(listAvailableIngredientAdapter);


            EditText etQuantity = dialog.findViewById(R.id.et_quantity_dialog);

            Button btnOk = dialog.findViewById(R.id.btn_ok_dialog);
            btnOk.setOnClickListener(v1 -> {
                int position = spIngredients.getSelectedItemPosition();
                String quantity = etQuantity.getText().toString();

                Ingredient ingredient = ingredients.get(position);
                globalStorage.getRecipeData().addDetailIngredient(new DetailIngredient(ingredient, quantity));
                addedDetailIngredientsAdapter.notifyDataSetChanged();
                dialog.cancel();
            });

            Button btnCancel = dialog.findViewById(R.id.btn_cancel_dialog);
            btnCancel.setOnClickListener(v12 -> {
                dialog.cancel();
            });

            dialog.show();
        });
        return view;
    }
}