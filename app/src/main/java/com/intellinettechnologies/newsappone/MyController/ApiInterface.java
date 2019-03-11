package com.intellinettechnologies.newsappone.MyController;


import com.intellinettechnologies.newsappone.MyModel.Example;

import retrofit2.Call;
import retrofit2.http.GET;

//REST API for Retrofit via the following interface.
public interface ApiInterface {
    @GET("top-headlines?country=us&apiKey=85495eebd3b24570a15c62a18f45e775")
    Call<Example> getJSON();
}
