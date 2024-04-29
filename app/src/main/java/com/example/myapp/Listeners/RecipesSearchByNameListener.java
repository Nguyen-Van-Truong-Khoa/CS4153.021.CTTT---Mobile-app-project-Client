package com.example.myapp.Listeners;

import com.example.myapp.Model.RandomRecipeApiResponse;

public interface RecipesSearchByNameListener {
    void didFetch (RandomRecipeApiResponse response, String message);
    void didError(String message);
}
