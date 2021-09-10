package com.finalexam.cookingapp.viewmodel;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.finalexam.cookingapp.R;
import com.finalexam.cookingapp.model.entity.Category;
import com.finalexam.cookingapp.view.activity.AddRecipeActivity;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {
    private List<Category> categories;

    public CategoryAdapter(List<Category> categories) {
        this.categories = categories;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category, parent, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.CategoryViewHolder holder, int position) {
        Category category = categories.get(position);
        if (category == null) return;

        int image = holder.itemView.getResources().getIdentifier(category.getImageID(), "drawable", holder.itemView.getContext().getPackageName());
        holder.ibLogo.setImageResource(image);
        holder.tvCategoryName.setText(category.getCategoryName());
        holder.categoryName = category.getCategoryName();
        holder.categoryID = category.getId();
    }

    @Override
    public int getItemCount() {
        if (categories != null)
            return categories.size();
        return 0;
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder {
        private CircleImageView ibLogo;
        private ImageButton ibAdd;
        private TextView tvCategoryName;

        private int categoryID;
        private String categoryName;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            ibLogo = itemView.findViewById(R.id.ib_logo);
            tvCategoryName = itemView.findViewById(R.id.tv_category_name);
            ibAdd = itemView.findViewById(R.id.ib_add);

            ibAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), AddRecipeActivity.class);
                    intent.putExtra("categoryName", categoryName);
                    intent.putExtra("categoryID", categoryID);
                    v.getContext().startActivity(intent);
                }
            });
        }
    }
}