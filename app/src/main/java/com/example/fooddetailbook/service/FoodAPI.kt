package com.example.fooddetailbook.service

import com.example.fooddetailbook.model.Food
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET

interface FoodAPI {
    @GET("erkindil/Json/main/fooddetail.json")
    fun getFood(): Single<List<Food>>
}