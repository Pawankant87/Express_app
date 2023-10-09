package dev.pawan.express_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import android.app.Activity;
import android.os.Bundle;

import org.imaginativeworld.whynotimagecarousel.model.CarouselItem;

import java.util.ArrayList;

import dev.pawan.express_app.adapters.CategoryAdapter;
import dev.pawan.express_app.adapters.ProductAdapter;
import dev.pawan.express_app.databinding.ActivityHomeScreenBinding;
import dev.pawan.express_app.model.Category;
import dev.pawan.express_app.model.Product;

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

    }

    private void initSlider() {
        binding.carousel.addData(new CarouselItem("https://tutorials.mianasad.com/ecommerce/uploads/news/Big%20Sale%20Start.jpg","adds"));
        binding.carousel.addData(new CarouselItem("https://tutorials.mianasad.com/ecommerce/uploads/news/guhblnj%20o79jpokpil.jpg","pro"));
        binding.carousel.addData(new CarouselItem("https://tutorials.mianasad.com/ecommerce/uploads/news/cyvublojmlklk%20jnm.jpg","cloth"));
    }

    void  initCategories(){

        categories = new ArrayList<>();
        categories.add(new Category("cloth","https://tutorials.mianasad.com/ecommerce/uploads/category/1696341816694.png",""," same discretion",1));
        categories.add(new Category("cloth","https://tutorials.mianasad.com/ecommerce/uploads/category/1696341816694.png",""," same discretion",2));
        categories.add(new Category("cloth","https://tutorials.mianasad.com/ecommerce/uploads/category/1696341816694.png",""," same discretion",3));
        categories.add(new Category("cloth","https://tutorials.mianasad.com/ecommerce/uploads/category/1696341816694.png",""," same discretion",4));
        categoryAdapter = new CategoryAdapter(this,categories);

        GridLayoutManager gridLayoutManager =new GridLayoutManager(this,4);
        binding.categoriesList.setLayoutManager(gridLayoutManager);
        binding.categoriesList.setAdapter(categoryAdapter);

    }
    void initProducts(){

        products = new ArrayList<>();
        products.add(new Product("Kurti","https://tutorials.mianasad.com/ecommerce/uploads/product/1695574713296.jpg","",230,50,1,1,1));
        products.add(new Product("Kurti","https://tutorials.mianasad.com/ecommerce/uploads/product/1695574713296.jpg","",230,50,1,1,1));
        products.add(new Product("Kurti","https://tutorials.mianasad.com/ecommerce/uploads/product/1695574713296.jpg","",230,50,1,1,1));
        products.add(new Product("Kurti","https://tutorials.mianasad.com/ecommerce/uploads/product/1695574713296.jpg","",230,50,1,1,1));
        products.add(new Product("Kurti","https://tutorials.mianasad.com/ecommerce/uploads/product/1695574713296.jpg","",230,50,1,1,1));

       productAdapter = new ProductAdapter(this,products);

        GridLayoutManager gridLayoutManager =new GridLayoutManager(this,2);
        binding.productList.setLayoutManager(gridLayoutManager);
        binding.productList.setAdapter(productAdapter);
    }
}