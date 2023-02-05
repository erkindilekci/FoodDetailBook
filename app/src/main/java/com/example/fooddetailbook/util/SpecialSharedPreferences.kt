package com.example.fooddetailbook.util

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

class SpecialSharedPreferences {

    companion object {
        private var sharedPrefences: SharedPreferences? = null
        private val TIME = "time"

        @Volatile private var instance: SpecialSharedPreferences? = null

        private val lock = Any()
        operator fun invoke(context: Context) : SpecialSharedPreferences = instance?: synchronized(lock) {
            instance ?: makeSpecialSharedPrefences(context).also {
                instance = it
            }
        }

        private fun makeSpecialSharedPrefences(context: Context): SpecialSharedPreferences{
            sharedPrefences = androidx.preference.PreferenceManager.getDefaultSharedPreferences(context)
            return SpecialSharedPreferences()
        }
    }

    fun saveTime(time: Long){
        sharedPrefences?.edit(commit = true){
            putLong(TIME, time)
        }
    }

    fun getTime() = sharedPrefences?.getLong(TIME, 0)
}