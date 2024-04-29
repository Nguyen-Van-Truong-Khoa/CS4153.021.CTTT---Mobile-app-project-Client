package com.example.myapp.Listeners;

import com.example.myapp.Model.RecipeInformation;

public interface RecipeDetailsListener {
        void didFetch (RecipeInformation response, String message);
        void didError(String message);
}
