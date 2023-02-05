package com.example.fooddetailbook.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.example.fooddetailbook.model.Food
import com.example.fooddetailbook.service.FoodAPIService
import com.example.fooddetailbook.service.FoodDatabase
import com.example.fooddetailbook.util.SpecialSharedPreferences
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.observers.DisposableSingleObserver
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.launch

class FoodListViewModel(application: Application): BaseViewModel(application) {
    val foods = MutableLiveData<List<Food>>()
    val foodWarningMessage = MutableLiveData<Boolean>()
    val foodLoading = MutableLiveData<Boolean>()
    private val updateTime = 10 * 60 * 1000 * 1000 * 1000L

    private val foodAPIService = FoodAPIService()
    private val disposable = CompositeDisposable()
    private val specialSharedPreferences = SpecialSharedPreferences(getApplication())

    fun refreshData(){
        val saveTime = specialSharedPreferences.getTime()
        if (saveTime != null && saveTime!= 0L && System.nanoTime() - saveTime < updateTime){
            getSqlData()
        } else {
            getInternetData()
        }
    }

    fun refreshFromInternet(){
        getInternetData()
    }

    private fun getSqlData(){
        foodLoading.value = true

        launch {
            val foodList = FoodDatabase(getApplication()).foodDao().getAllFood()
            showFoods(foodList)
            Toast.makeText(getApplication(), "From Room", Toast.LENGTH_LONG).show()
        }
    }

    private fun getInternetData(){
        foodLoading.value = true

        disposable.add(
            foodAPIService.getData()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<Food>>() {
                    override fun onSuccess(t: List<Food>) {
                        keepSql(t)
                        Toast.makeText(getApplication(), "From Internet", Toast.LENGTH_LONG).show()
                    }

                    override fun onError(e: Throwable) {
                        foodWarningMessage.value = true
                        foodLoading.value = false
                        e.printStackTrace()
                    }

                })
        )
    }

    private fun showFoods(foodList: List<Food>){
        foods.value = foodList
        foodWarningMessage.value = false
        foodLoading.value = false
    }

    private fun keepSql(foodList: List<Food>){
        launch {
            val dao = FoodDatabase(getApplication()).foodDao()
            dao.deleteAllFood()
            val uuidList = dao.insertAll(*foodList.toTypedArray())
            var i = 0
            while (i < foodList.size){
                foodList[i].uuid = uuidList[i].toInt()
                i+=1
            }
            showFoods(foodList)
        }
        specialSharedPreferences.saveTime(System.nanoTime())
    }




}