package com.example.myapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import androidx.appcompat.widget.SearchView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.myapp.Adapters.RandomRecipeAdapter;
import com.example.myapp.Listeners.RandomRecipeResponseListener;
import com.example.myapp.Listeners.RecipeClickListener;
import com.example.myapp.Listeners.RecipesSearchByNameListener;
import com.example.myapp.Model.RandomRecipeApiResponse;

import okhttp3.MediaType;
import okhttp3.RequestBody;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class homepage extends AppCompatActivity {

    ProgressDialog dialog;
    RequestManager manager;
    RandomRecipeAdapter randomRecipeAdapter;
    RecyclerView recyclerView;
    Spinner spinner;

    List<String> tags = new ArrayList<>();
    SearchView searchView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        dialog = new ProgressDialog( this);
        dialog.setTitle("Loading...");

        searchView = findViewById(R.id.searchView_home);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String name) {
                JSONObject jsonBody = new JSONObject();
                try {
                    jsonBody.put("name", name);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), jsonBody.toString());
                manager.searchRecipeByName(recipesSearchByNameListener, requestBody);
                dialog.show();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        spinner = findViewById(R.id.spinner_tags);
        ArrayAdapter arrayAdapter = ArrayAdapter.createFromResource(
                this,
                R.array.tags,
                R.layout.spinner_text
        );
        arrayAdapter.setDropDownViewResource(R.layout.spinner_inner_text);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(spinnerSelectedListener);


        manager = new RequestManager( this);
//        manager.getRandomRecipes(randomRecipeResponseListener, );
//        dialog.show();
    }
    private final RandomRecipeResponseListener randomRecipeResponseListener = new RandomRecipeResponseListener() {

        @Override
        public void didFetch(RandomRecipeApiResponse response, String message) {
            recyclerView = findViewById(R.id.recycler_random);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new GridLayoutManager(homepage.this, 1));
            randomRecipeAdapter = new RandomRecipeAdapter(homepage.this, response.recipes, recipeClickListener);
            recyclerView.setAdapter(randomRecipeAdapter);
            dialog.dismiss();
        }
        @Override
        public void didError(String message) {
            Toast.makeText(homepage.this, message, Toast.LENGTH_SHORT).show();
        }
    };

    private final RecipesSearchByNameListener recipesSearchByNameListener = new RecipesSearchByNameListener() {

        @Override
        public void didFetch(RandomRecipeApiResponse response, String message) {
            recyclerView = findViewById(R.id.recycler_random);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new GridLayoutManager(homepage.this, 1));
            randomRecipeAdapter = new RandomRecipeAdapter(homepage.this, response.recipes, recipeClickListener);
            recyclerView.setAdapter(randomRecipeAdapter);
            dialog.dismiss();
        }
        @Override
        public void didError(String message) {
            Toast.makeText(homepage.this, message, Toast.LENGTH_SHORT).show();
        }
    };

    private final AdapterView.OnItemSelectedListener spinnerSelectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            tags.clear();
            tags.add(adapterView.getSelectedItem().toString());
            manager.getRandomRecipes(randomRecipeResponseListener, tags);
            dialog.show();
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }

    };
    private final RecipeClickListener recipeClickListener = new RecipeClickListener() {
        @Override
        public void onRecipeClicked(String id) {
            startActivity(new Intent(homepage.this, RecipesDetailsActivity.class)
                    .putExtra("id", id));
            //Toast.makeText(homepage.this, id, Toast.LENGTH_SHORT).show();
        }
    };
}