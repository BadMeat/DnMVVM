package com.dolan.arif.dncatapp.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

/**
 * Created by Bencoleng on 07/10/2019.
 */
@Dao
interface CatDao {

    @Insert
    suspend fun insert(vararg cat: Cat): List<Long>

    @Query("select * from Cat")
    suspend fun select(): List<Cat>

    @Query("SELECT * FROM Cat WHERE uuid = :id")
    suspend fun getCat(id: Int): Cat

    @Query("DELETE FROM Cat")
    suspend fun deleteCat()
}