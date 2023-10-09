package dev.pawan.express_app.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import dev.pawan.express_app.R;
import dev.pawan.express_app.databinding.ItemCategoryBinding;
import dev.pawan.express_app.model.Category;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {

    Context context;
    ArrayList<Category> categories;

    public  CategoryAdapter(Context context,ArrayList<Category> categories){
        this.categories =categories;
        this.context=context;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CategoryViewHolder(LayoutInflater.from(context).inflate(R.layout.item_category,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        Category category = categories.get(position);
        holder.binding.label.setText(category.getName());
        Glide.with(context).load(category.getIcon()).into(holder.binding.image);



    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder{
        ItemCategoryBinding binding;
        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            binding =ItemCategoryBinding.bind(itemView);
        }
    }
}
