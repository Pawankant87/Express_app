package dev.pawan.express_app;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.mancj.materialsearchbar.MaterialSearchBar;

import org.imaginativeworld.whynotimagecarousel.model.CarouselItem;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import dev.pawan.express_app.adapters.CategoryAdapter;
import dev.pawan.express_app.adapters.ProductAdapter;
import dev.pawan.express_app.databinding.ActivityHomeScreenBinding;
import dev.pawan.express_app.model.Category;
import dev.pawan.express_app.model.Product;
import dev.pawan.express_app.utils.Constants;

public class HomeScreenActivity extends AppCompatActivity {

    CategoryAdapter categoryAdapter;
    ArrayList<Category> categories;

    ProductAdapter productAdapter;
    ArrayList<Product> products;

    ActivityHomeScreenBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding =ActivityHomeScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initProducts();
        initCategories();
        initSlider();
        getCategories();
        getProduct();

        binding.searchBar.setOnSearchActionListener(new MaterialSearchBar.OnSearchActionListener() {
            @Override
            public void onSearchStateChanged(boolean enabled) {

            }

            @Override
            public void onSearchConfirmed(CharSequence text) {
                Intent intent = new Intent(HomeScreenActivity.this, SearchActivity.class);
                intent.putExtra("query", text.toString());
                startActivity(intent);
            }

            @Override
            public void onButtonClicked(int buttonCode) {

            }
        });


    }

    private void initSlider() {
        getRecentOffers();

    }

    void  initCategories(){

        categories = new ArrayList<>();
        categoryAdapter = new CategoryAdapter(this,categories);
        GridLayoutManager gridLayoutManager =new GridLayoutManager(this,4);
        binding.categoriesList.setLayoutManager(gridLayoutManager);
        binding.categoriesList.setAdapter(categoryAdapter);

    }
    void  getCategories(){
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest request =new StringRequest(Request.Method.GET, Constants.GET_CATEGORIES_URL, response -> {
            try {
                JSONObject mainObject =new JSONObject(response);
                if(mainObject.getString("status").equals("success")){
                    JSONArray categoriesArray =mainObject.getJSONArray("categories");
                    for(int i=0; i< categoriesArray.length();i++){
                        JSONObject object = categoriesArray.getJSONObject(i);
                        Category category = new Category(
                                object.getString("name"),
                               Constants.CATEGORIES_IMAGE_URL+object.getString("icon"),
                                object.getString("brief"),
                                object.getInt("id")

                        );
                        categories.add(category);
                    }
                    categoryAdapter.notifyDataSetChanged();
                 }
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }

            Log.e("err",response);

        }, error -> {

        });

        queue.add(request);

    }
    void initProducts(){
        products = new ArrayList<>();
        productAdapter = new ProductAdapter(this,products);

        GridLayoutManager gridLayoutManager =new GridLayoutManager(this,2);
        binding.productList.setLayoutManager(gridLayoutManager);
        binding.productList.setAdapter(productAdapter);
    }

   void getProduct(){
        RequestQueue queue = Volley.newRequestQueue(this);

        StringRequest request =new StringRequest(Request.Method.GET, Constants.GET_PRODUCTS_URL, response -> {
            try {
                JSONObject mainObject =new JSONObject(response);
                if (mainObject.getString("status").equals("success")) {
                    JSONArray productArray =mainObject.getJSONArray("products");
                    for(int i=0; i<productArray.length(); i++ ){
                        JSONObject object = productArray.getJSONObject(i);
                        Product product = new Product(
                                object.getString("name"),
                                Constants.PRODUCTS_IMAGE_URL+ object.getString("image"),
                                object.getString("status"),
                                object.getDouble("price"),
                                object.getDouble("price_discount"),
                                object.getInt("stock"),
                                object.getInt("id")

                        );

                        products.add(product);
                    }
                    productAdapter.notifyDataSetChanged();
                }
                Log.e("err",response);

            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }, error -> {});
        queue.add(request);
    }
    void getRecentOffers() {
        RequestQueue queue = Volley.newRequestQueue(this);

        StringRequest request = new StringRequest(Request.Method.GET, Constants.GET_OFFERS_URL, response -> {
            try {
                JSONObject object = new JSONObject(response);
                if(object.getString("status").equals("success")) {
                    JSONArray offerArray = object.getJSONArray("news_infos");
                    for(int i =0; i < offerArray.length(); i++) {
                        JSONObject childObj =  offerArray.getJSONObject(i);
                        binding.carousel.addData(
                                new CarouselItem(
                                        Constants.NEWS_IMAGE_URL + childObj.getString("image"),
                                        childObj.getString("title")
                                )
                        );
                    }
                }
                Log.e("err",response);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> {});
        queue.add(request);
    }


}