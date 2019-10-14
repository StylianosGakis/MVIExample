package se.stylianosgakis.mviexample.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import se.stylianosgakis.mviexample.util.LiveDataCallAdapterFactory

object RetrofitBuilder {

    private const val BASE_URL = "https://open-api.xyz/"

    val retrofitBuilder: Retrofit.Builder by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addCallAdapterFactory(LiveDataCallAdapterFactory())
            .addConverterFactory(GsonConverterFactory.create())
    }

    val apiService: ApiService by lazy {
        retrofitBuilder
            .build()
            .create(ApiService::class.java)
    }

}