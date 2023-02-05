package com.example.fooddetailbook.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fooddetailbook.model.Food
import com.example.fooddetailbook.service.FoodDatabase
import kotlinx.coroutines.launch

class FoodDetailViewModel(application: Application): BaseViewModel(application) {
    val foodLivaData = MutableLiveData<Food>()

    fun getRoomData(uuid: Int){
        launch {
            val dao = FoodDatabase(getApplication()).foodDao()
            val food = dao.getFood(uuid)
            foodLivaData.value = food
        }
    }
}