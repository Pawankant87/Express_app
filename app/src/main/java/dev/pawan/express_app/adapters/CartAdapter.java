package dev.pawan.express_app.adapters;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import com.hishd.tinycart.model.Cart;
import com.hishd.tinycart.util.TinyCartHelper;

import java.util.ArrayList;
import java.util.Objects;

import dev.pawan.express_app.R;
import dev.pawan.express_app.databinding.ItemCartBinding;
import dev.pawan.express_app.databinding.QuantityDialogBinding;
import dev.pawan.express_app.model.Product;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    Context context;
    ArrayList<Product> products;
    CartListener cartListener;
    Cart cart;

    public interface CartListener {
        public void onQuantityChanged();
    }


    public CartAdapter(Context context, ArrayList<Product> products, CartListener cartListener) {
        this.context = context;
        this.products = products;
        this.cartListener = cartListener;
        cart = TinyCartHelper.getCart();
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CartViewHolder(LayoutInflater.from(context).inflate(R.layout.item_cart, parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        Product product = products.get(position);
        Glide.with(context)
                .load(product.getImage())
                .into(holder.binding.image);

        holder.binding.name.setText(product.getName());
        holder.binding.price.setText(String.format("₹ %s", product.getDiscount()));
        holder.binding.quantity.setText(product.getQuantity() + " item(s)");

        holder.itemView.setOnClickListener(view -> {
            QuantityDialogBinding quantityDialogBinding = QuantityDialogBinding.inflate(LayoutInflater.from(context));

            AlertDialog dialog = new AlertDialog.Builder(context)
                    .setView(quantityDialogBinding.getRoot())
                    .create();

            Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable());

            quantityDialogBinding.productName.setText(product.getName());
            quantityDialogBinding.productStock.setText("Stock: " + product.getStock());
            quantityDialogBinding.quantity.setText(String.valueOf(product.getQuantity()));
            int stock = product.getStock();


            quantityDialogBinding.plusBtn.setOnClickListener(view1 -> {
                int quantity = product.getQuantity();
                quantity++;

                if(quantity>product.getStock()) {
                    Toast.makeText(context, "Max stock available: "+ product.getStock(), Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    product.setQuantity(quantity);
                    quantityDialogBinding.quantity.setText(String.valueOf(quantity));
                }

                notifyDataSetChanged();
                cart.updateItem(product, product.getQuantity());
                cartListener.onQuantityChanged();
            });

            quantityDialogBinding.minusBtn.setOnClickListener(view12 -> {
                int quantity = product.getQuantity();
                if(quantity > 1)
                    quantity--;
                product.setQuantity(quantity);
                quantityDialogBinding.quantity.setText(String.valueOf(quantity));

                notifyDataSetChanged();
                cart.updateItem(product, product.getQuantity());
                cartListener.onQuantityChanged();
            });

            quantityDialogBinding.saveBtn.setOnClickListener(view13 -> {
                dialog.dismiss();
//                        notifyDataSetChanged();
//                        cart.updateItem(product, product.getQuantity());
//                        cartListener.onQuantityChanged();
            });

            dialog.show();
        });
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public class CartViewHolder extends RecyclerView.ViewHolder {

        ItemCartBinding binding;
        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ItemCartBinding.bind(itemView);
        }
    }
}
