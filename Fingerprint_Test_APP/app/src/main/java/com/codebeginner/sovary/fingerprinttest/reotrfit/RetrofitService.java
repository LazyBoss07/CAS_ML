package com.codebeginner.sovary.fingerprinttest.reotrfit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class RetrofitService {

  private Retrofit retrofit;

  public RetrofitService() {
    initializeRetrofit();
  }

  private void initializeRetrofit() {
    OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
    httpClient.addInterceptor(chain -> {
      Request originalRequest = chain.request();
      Request.Builder requestBuilder = originalRequest.newBuilder()
              .header("Accept", "application/json")  // Set Accept header to indicate JSON response
              .method(originalRequest.method(), originalRequest.body());
      Request request = requestBuilder.build();
      return chain.proceed(request);
    });
    Gson gson = new GsonBuilder().setLenient().create();//.
    retrofit = new Retrofit.Builder()
        .baseUrl("http://192.168.246.144:8080")
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build();
//    retrofit = new Retrofit.Builder()
//            .baseUrl("http://192.168.246.144:8080")
//            .addConverterFactory(ScalarsConverterFactory.create())
//            .build();


  }

  public Retrofit getRetrofit() {
    return retrofit;
  }
}
