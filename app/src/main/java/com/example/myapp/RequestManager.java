package com.example.myapp;

import android.content.Context;

import com.example.myapp.Listeners.RandomRecipeResponseListener;
import com.example.myapp.Model.RandomRecipeApiResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;


public class RequestManager {
    Context context;
    String url_server = "http://192.168.1.16:4000";//context.getString(R.string.urlserver);
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(url_server)
            .addConverterFactory (GsonConverterFactory.create())
            .build();


    public RequestManager (Context context) { this.context = context; }

    public void getRandomRecipes (RandomRecipeResponseListener listener , List<String> tags){
        CallRandomRecipes callRandomRecipes = retrofit.create(CallRandomRecipes.class);
        Call<RandomRecipeApiResponse> call = callRandomRecipes.callRandomRecipe("10", tags);
        call.enqueue(new Callback<RandomRecipeApiResponse>() {
            @Override
            public void onResponse(Call<RandomRecipeApiResponse> call, Response<RandomRecipeApiResponse> response) {
                if (!response.isSuccessful()) {
                    listener.didError(response.message());
                    System.out.println(response);
                    return;
                }
                listener.didFetch(response.body(), response.message());
            }

            @Override
            public void onFailure(Call<RandomRecipeApiResponse> call, Throwable t) {
                listener.didError(t.getMessage());
            }
        });
    }

    private interface CallRandomRecipes{
        @GET("recipes/rand")
        Call<RandomRecipeApiResponse> callRandomRecipe (
                @Query("limit") String limit,
                @Query("tags") List<String> tags
        );
    }
}