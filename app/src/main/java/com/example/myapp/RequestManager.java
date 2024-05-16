package com.example.myapp;

import android.content.Context;

import com.example.myapp.Listeners.RandomRecipeResponseListener;
import com.example.myapp.Listeners.RecipeDetailsListener;
import com.example.myapp.Listeners.RecipeNutritionListener;
import com.example.myapp.Listeners.RecipesSearchByNameListener;
import com.example.myapp.Model.RandomRecipeApiResponse;
import com.example.myapp.Model.RecipeInformation;
import com.example.myapp.Model.RecipeNutritions;
import com.example.myapp.Model.Recipes;

import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;


public class RequestManager {
    Context context;
    //String url_server = "http://26.68.212.226:4000";// find local ip address
    String url_server = "https://cs4153-021-cttt-mobile-app-project-server.onrender.com"; //link url online server
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

    public void getRecipeDetails (RecipeDetailsListener listener, int id){
        CallRecipeDetails callRecipeDetails = retrofit.create(CallRecipeDetails.class);
        Call<RecipeInformation> call = callRecipeDetails.CallRecipeDetails(id);
        call.enqueue(new Callback<RecipeInformation>() {
            @Override
            public void onResponse(Call<RecipeInformation> call, Response<RecipeInformation> response) {
                if (!response.isSuccessful()) {
                    listener.didError(response.message());
                    return;
                }
                listener.didFetch(response.body(), response.message());
            }

            @Override
            public void onFailure(Call<RecipeInformation> call, Throwable t) {
                listener.didError(t.getMessage());
            }
        });
    }

    public void getRecipeNutritions (RecipeNutritionListener listener, int id){
        CallRecipeNutritions callRecipeNutritions = retrofit.create(CallRecipeNutritions.class);
        Call<RecipeNutritions> call = callRecipeNutritions.CallRecipeNutritions(id);
        call.enqueue(new Callback<RecipeNutritions>() {
            @Override
            public void onResponse(Call<RecipeNutritions> call, Response<RecipeNutritions> response) {
                if (!response.isSuccessful()) {
                    listener.didError(response.message());
                    return;
                }
                listener.didFetch(response.body(), response.message());
            }

            @Override
            public void onFailure(Call<RecipeNutritions> call, Throwable t) {
                listener.didError(t.getMessage());
            }
        });
    }

    public void searchRecipeByName (RecipesSearchByNameListener listener , RequestBody requestBody){
        CallSearchRecipesByName callSearchRecipesByName = retrofit.create(CallSearchRecipesByName.class);
        Call<RandomRecipeApiResponse> call = callSearchRecipesByName.CallSearchRecipesByName(requestBody);
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

    private interface CallRecipeDetails{
        @GET("recipes/{id}/information")
        Call<RecipeInformation> CallRecipeDetails (
                @Path("id") int id
        );
    }

    private interface CallRecipeNutritions {
        @GET("recipes/{id}/nutrients")
        Call<RecipeNutritions> CallRecipeNutritions(
                @Path("id") int id
        );
    }

    private interface CallSearchRecipesByName {
        @POST("recipes/search")
        Call<RandomRecipeApiResponse> CallSearchRecipesByName(
                @Body RequestBody body
        );
    }

}