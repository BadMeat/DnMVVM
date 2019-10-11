package com.dolan.arif.dncatapp.viewmodel

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.dolan.arif.dncatapp.model.Cat
import com.dolan.arif.dncatapp.model.CatApiService
import com.dolan.arif.dncatapp.model.CatDatabase
import com.dolan.arif.dncatapp.utlis.SharedPreferencesHelper
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch

/**
 * Created by Bencoleng on 06/10/2019.
 */
class CatViewModel(application: Application) : BaseViewModel(application) {

    private var prefHelper = SharedPreferencesHelper(getApplication())
    private val apiService = CatApiService()
    private val disposable = CompositeDisposable()
    private val refreshTime = 10 * 1000 * 1000 * 1000L

    val catList = MutableLiveData<List<Cat>>()
    val isError = MutableLiveData<Boolean>()
    val isLoading = MutableLiveData<Boolean>()

    fun refresh() {
        val refresh = prefHelper.getUpdateTime()
        if (refresh != null && refresh != 0L && System.nanoTime() - refresh < refreshTime) {
            fetchFromLocal()
        } else {
            fetchFromRemote()
        }
    }

    fun byPassRefresh() {
        fetchFromRemote()
    }

    private fun fetchFromLocal() {
        isLoading.value = true
        launch {
            val database = CatDatabase(getApplication()).catDao().select()
            catRetrieved(database)
            Toast.makeText(getApplication(), "Data From Local", Toast.LENGTH_SHORT).show()
        }
    }


    private fun fetchFromRemote() {
        isLoading.value = true
        disposable.addAll(
            apiService.getCat()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<Cat>>() {
                    override fun onSuccess(cat: List<Cat>) {
                        storeLocally(cat)
                        Toast.makeText(getApplication(), "Data From Remote", Toast.LENGTH_SHORT)
                            .show()
                    }

                    override fun onError(e: Throwable) {
                        isError.value = true
                        isLoading.value = false
                        Log.d("ERROR", "${e.printStackTrace()}")
                    }
                })
        )
    }

    private fun catRetrieved(cat: List<Cat>) {
        catList.value = cat
        isLoading.value = false
        isError.value = false
    }

    private fun storeLocally(cat: List<Cat>) {
        launch {
            val dao = CatDatabase.invoke(getApplication()).catDao()
            dao.deleteCat()
            val result = dao.insert(*cat.toTypedArray())
            var i = 0
            while (i < result.size) {
                cat[i].uuid = result[i].toInt()
                i++
            }
            catRetrieved(cat)
        }
        prefHelper.saveUpdateTime(System.nanoTime())
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}