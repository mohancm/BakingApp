package com.mohan.bakingapp.data;

import java.util.List;

import com.mohan.bakingapp.model.Recipe;
import com.mohan.bakingapp.helper.DbHelper;
import com.mohan.bakingapp.network.NetworkUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Headers;

/**
 * Created by mohancm on 28/11/17.
 */

public class RecipeDbHelper {

//    baking.json

    private static RecipeDbHelper instance;

    public static RecipeDbHelper getInstance() {

        if(instance == null) {
            instance = new RecipeDbHelper();
        }

        return instance;
    }

    public void getRecipes(final DbHelper helper) {

        NetworkService request = NetworkUtils.getRequest().create(NetworkService.class);
        request.getRecipes().enqueue(new Callback<List<Recipe>>() {

            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {

                helper.onSuccess(response.body());

            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {

                helper.onError(t.getLocalizedMessage());

            }

        });


    }

    public interface NetworkService {

        @Headers("Content-Type: application/json")
        @GET("/android-baking-app-json")
        Call<List<Recipe>> getRecipes();

    }

}