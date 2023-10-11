package dev.pawan.express_app.adapters;

import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import dev.pawan.express_app.CategoryActivity;
import dev.pawan.express_app.R;
import dev.pawan.express_app.databinding.ItemCategoryBinding;
import dev.pawan.express_app.model.Category;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {

    Context context;
    ArrayList<Category> categories;

    public CategoryAdapter(Context context, ArrayList<Category> categories) {
        this.context = context;
        this.categories = categories;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CategoryViewHolder(LayoutInflater.from(context).inflate(R.layout.item_category, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        Category category = categories.get(position);
        holder.binding.label.setText(Html.fromHtml(category.getName()));
        Glide.with(context)
                .load(category.getIcon())
                .into(holder.binding.image);

        holder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(context, CategoryActivity.class);
            intent.putExtra("catId", category.getId());
            intent.putExtra("categoryName", category.getName());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public static class CategoryViewHolder extends RecyclerView.ViewHolder {
        ItemCategoryBinding binding;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ItemCategoryBinding.bind(itemView);
        }
    }
}