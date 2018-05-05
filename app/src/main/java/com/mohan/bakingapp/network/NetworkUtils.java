package com.mohan.bakingapp.network;

import com.mohan.bakingapp.network.ApiConstants;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by mohancm on 05/05/18.
 */

public class NetworkUtils {

    public static Retrofit getRequest() {

        Retrofit.Builder builder = new Retrofit.Builder();
        builder.baseUrl(ApiConstants.API_BASE_URL);
        builder.addConverterFactory(GsonConverterFactory.create());

        return builder.build();
    }

}