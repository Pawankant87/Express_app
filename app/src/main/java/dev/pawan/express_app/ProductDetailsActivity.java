package dev.pawan.express_app;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.hishd.tinycart.model.Cart;
import com.hishd.tinycart.util.TinyCartHelper;

import org.json.JSONException;
import org.json.JSONObject;

import dev.pawan.express_app.databinding.ActivityProductDetailsBinding;
import dev.pawan.express_app.model.Product;
import dev.pawan.express_app.utils.Constants;

public class ProductDetailsActivity extends AppCompatActivity {

    ActivityProductDetailsBinding binding;

    Product currentProduct;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProductDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        String name = getIntent().getStringExtra("name");
        String image = getIntent().getStringExtra("image");
        int id = getIntent().getIntExtra("id", 0);
        double price = getIntent().getDoubleExtra("price", 0);
        double discount =getIntent().getDoubleExtra("discount",0);

        Glide.with(this)
                .load(image)
                .into(binding.productImage);
        binding.originalPriceTextView.setText(String.format("MRP ₹%s", price));
        binding.originalPriceTextView.setPaintFlags(binding.originalPriceTextView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        binding.discountedPriceTextView.setText(String.format("₹%s", discount));



        getProductDetails(id);
        Cart cart = TinyCartHelper.getCart();

        getSupportActionBar().setTitle(name);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        binding.addToCartBtn.setOnClickListener(view -> {
            cart.addItem(currentProduct,1);
            binding.addToCartBtn.setEnabled(false);
            binding.addToCartBtn.setText("Added in cart");
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.cart, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId() == R.id.cart) {
            startActivity(new Intent(this, CartActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

    void getProductDetails(int id) {
        RequestQueue queue = Volley.newRequestQueue(this);

        String url = Constants.GET_PRODUCT_DETAILS_URL + id;

        StringRequest request = new StringRequest(Request.Method.GET, url, response -> {
            try {
                JSONObject object = new JSONObject(response);
                if(object.getString("status").equals("success")) {
                    JSONObject product = object.getJSONObject("product");
                    String description = product.getString("description");
                    binding.productDescription.setText(
                            Html.fromHtml(description)
                    );

                    currentProduct = new Product(
                            product.getString("name"),
                            Constants.PRODUCTS_IMAGE_URL + product.getString("image"),
                            product.getString("status"),
                            product.getDouble("price"),
                            product.getDouble("price_discount"),
                            product.getInt("stock"),
                            product.getInt("id")
                    );

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> {

        });

        queue.add(request);
    }
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

}
