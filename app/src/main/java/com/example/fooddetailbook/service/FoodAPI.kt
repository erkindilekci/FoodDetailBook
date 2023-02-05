package com.example.fooddetailbook.service

import com.example.fooddetailbook.model.Food
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET

interface FoodAPI {
    // BASE_URL -> https://raw.githubusercontent.com/
    // denemefalan/Json/main/jsononline-net.json

    //https://raw.githubusercontent.com/denemefalan/json/main/jsononline-net%20(1).json
    @GET("denemefalan/json/main/jsononline-net%20(1).json")
    fun getFood(): Single<List<Food>>
}