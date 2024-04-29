package com.example.myapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapp.Adapters.IngredientsAdapter;
import com.example.myapp.Listeners.RecipeDetailsListener;
import com.example.myapp.Listeners.RecipeNutritionListener;
import com.example.myapp.Model.RecipeInformation;
import com.example.myapp.Model.RecipeNutritions;
import com.squareup.picasso.Picasso;

public class RecipesDetailsActivity extends AppCompatActivity {
int id;
TextView textView_meal_name, textView_meal_types, textView_meal_methods;
TextView textView_calories, textView_protein,textView_fat,textView_sodium ;
ImageView imageView_meal_image;
RecyclerView recyclerView_meal_ingredients;
RequestManager manager;
ProgressDialog dialog;
IngredientsAdapter ingredientsAdapter;

Button button_meal_nutrition;

Dialog nutritionFactsDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes_details);
        findViews();
        id = Integer.parseInt(getIntent().getStringExtra("id"));
        manager = new RequestManager(this);
        manager.getRecipeDetails(recipeDetailsListener, id);
        dialog =new ProgressDialog(this);
        dialog.setTitle("Loading...");
        dialog.show();
    }

    private void findViews() {
        textView_meal_name = findViewById(R.id.textView_meal_name);
        textView_meal_types = findViewById(R.id.textView_meal_types);
        textView_meal_methods = findViewById(R.id.textView_meal_methods);
        imageView_meal_image = findViewById(R.id.imageView_meal_image);
        recyclerView_meal_ingredients = findViewById(R.id.recyclerView_meal_ingredients);
        button_meal_nutrition = findViewById(R.id.button_meal_nutrition);
        button_meal_nutrition.setOnClickListener(view -> {
            showNutritionFactsDialog();
            textView_calories = nutritionFactsDialog.findViewById(R.id.textView_calories);
            textView_protein = nutritionFactsDialog.findViewById(R.id.textView_protein);
            textView_fat = nutritionFactsDialog.findViewById(R.id.textView_fat);
            textView_sodium = nutritionFactsDialog.findViewById(R.id.textView_sodium);
            manager = new RequestManager(this);
            manager.getRecipeNutritions(recipeNutritionListener, id);
        });
    }

    private void showNutritionFactsDialog() {
        nutritionFactsDialog = new Dialog(this);
        nutritionFactsDialog.setContentView(R.layout.nutrion_chart_layout);
        nutritionFactsDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        nutritionFactsDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        nutritionFactsDialog.setCanceledOnTouchOutside(true);
        nutritionFactsDialog.show();
    }

    private final RecipeDetailsListener recipeDetailsListener = new RecipeDetailsListener() {

        @Override
        public void didFetch(RecipeInformation response, String message) {
            dialog.dismiss();
            textView_meal_name.setText(response.title);
            textView_meal_types.setText("Types: " + response.types);
            textView_meal_methods.setText("Methods: " + response.methods);
            Picasso.get().load(response.image).into(imageView_meal_image);
            recyclerView_meal_ingredients.setHasFixedSize(true);
            recyclerView_meal_ingredients.setLayoutManager(new LinearLayoutManager(RecipesDetailsActivity.this, LinearLayoutManager.HORIZONTAL, false));
            ingredientsAdapter = new IngredientsAdapter(RecipesDetailsActivity.this, response.ingredients);
            recyclerView_meal_ingredients.setAdapter(ingredientsAdapter);
            
        }

        @Override
        public void didError(String message) {
            Toast.makeText(RecipesDetailsActivity.this, message, Toast.LENGTH_SHORT).show();
        }
    };

    private final RecipeNutritionListener recipeNutritionListener = new RecipeNutritionListener() {

        @Override
        public void didFetch(RecipeNutritions response, String message) {
            textView_calories.setText(String.valueOf(response.calories));
            textView_protein.setText(response.protein + " g");
            textView_fat.setText(response.fat + " g");
            textView_sodium.setText(response.sodium + " mg");
        }

        @Override
        public void didError(String message) {
            Toast.makeText(RecipesDetailsActivity.this, message, Toast.LENGTH_SHORT).show();
        }
    };
}