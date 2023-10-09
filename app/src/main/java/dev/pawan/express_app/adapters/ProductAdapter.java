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
import dev.pawan.express_app.databinding.ItemProductBinding;
import dev.pawan.express_app.model.Product;

public class ProductAdapter extends  RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    Context context;
     ArrayList<Product> products;

     public  ProductAdapter(Context context ,ArrayList<Product> products){
         this.context =context;
         this.products =products;
     }
    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ProductViewHolder(LayoutInflater.from(context).inflate(R.layout.item_product,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {

         Product product =products.get(position);
         holder.binding.label.setText(product.getName());
        Glide.with(context).load(product.getImage()).into(holder.binding.image);
        holder.binding.price.setText(String.format("Rs.%s", product.getPrice()));
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder{
        ItemProductBinding binding;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);

            binding = ItemProductBinding.bind(itemView);

        }
    }
}
