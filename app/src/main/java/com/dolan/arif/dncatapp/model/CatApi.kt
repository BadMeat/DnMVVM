package com.dolan.arif.dncatapp.model

import io.reactivex.Single
import retrofit2.http.GET

/**
 * Created by Bencoleng on 06/10/2019.
 */
interface CatApi {

    @GET("DevTides/DogsApi/master/dogs.json")
    fun getCat(): Single<List<Cat>>

}