package com.dolan.arif.dncatapp.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.dolan.arif.dncatapp.model.Cat
import com.dolan.arif.dncatapp.model.CatDatabase
import kotlinx.coroutines.launch

/**
 * Created by Bencoleng on 06/10/2019.
 */
class CatDetailViewModel(application: Application) : BaseViewModel(application) {

    val cat = MutableLiveData<Cat>()
    val isLoading = MutableLiveData<Boolean>()

    fun fetchData(id: Int) {
        isLoading.value = true
        launch {
            val getCat = CatDatabase(getApplication()).catDao().getCat(id)
            cat.value = getCat
            isLoading.value = false
        }
    }
}