package com.example.fooddetailbook.service

import com.example.fooddetailbook.model.Food
import io.reactivex.rxjava3.core.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class FoodAPIService {
    // BASE_URL -> https://raw.githubusercontent.com/
    // denemefalan/Json/main/jsononline-net.json

    private val BASE_URL = "https://raw.githubusercontent.com/"
    private val api = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .build()
        .create(FoodAPI::class.java)

    fun getData(): Single<List<Food>>{
        return api.getFood()
    }
}