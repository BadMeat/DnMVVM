package com.dolan.arif.dncatapp.model

import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by Bencoleng on 06/10/2019.
 */
class CatApiService {

    private val BASE_URL = "https://raw.githubusercontent.com/"

    private val api = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .baseUrl(BASE_URL)
        .build()
        .create(CatApi::class.java)

    fun getCat(): Single<List<Cat>> {
        return api.getCat()
    }
}