package com.dolan.arif.dncatapp.utlis

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import androidx.core.content.edit

/**
 * Created by Bencoleng on 09/10/2019.
 */
class SharedPreferencesHelper {

    companion object {

        private const val TIME = "com.dolan.arif.dncatapp.utlis.time"
        private var pref: SharedPreferences? = null

        @Volatile
        private var instance: SharedPreferencesHelper? = null

        private fun buildHelper(context: Context): SharedPreferencesHelper {
            pref = PreferenceManager.getDefaultSharedPreferences(context)
            return SharedPreferencesHelper()
        }

        operator fun invoke(context: Context): SharedPreferencesHelper =
            instance ?: synchronized(Any()) {
                instance ?: buildHelper(context).also {
                    instance = it
                }
            }
    }

    fun saveUpdateTime(time: Long) {
        pref?.edit(commit = true) {
            putLong(TIME, time)
        }
    }

    fun getUpdateTime() = pref?.getLong(TIME,0)
}