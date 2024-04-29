package com.example.myapp.Listeners;

import com.example.myapp.Model.RecipeNutritions;

public interface RecipeNutritionListener {
    void didFetch (RecipeNutritions response, String message);
    void didError(String message);
}
