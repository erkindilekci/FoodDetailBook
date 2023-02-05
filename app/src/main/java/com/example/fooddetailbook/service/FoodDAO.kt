package com.example.fooddetailbook.service

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.fooddetailbook.model.Food

@Dao
interface FoodDAO {
    @Insert
    suspend fun insertAll(vararg food: Food) : List<Long>

    @Query("SELECT * FROM Food")
    suspend fun getAllFood() : List<Food>

    @Query("SELECT * FROM Food WHERE uuid = :foodId")
    suspend fun getFood(foodId: Int): Food

    @Query("DELETE FROM Food")
    suspend fun deleteAllFood()

}