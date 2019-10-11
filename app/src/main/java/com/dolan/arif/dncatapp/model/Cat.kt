package com.dolan.arif.dncatapp.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by Bencoleng on 06/10/2019.
 */
@Entity
data class Cat(
    @ColumnInfo(name = "cat_id")
    var id: Int?,

    @ColumnInfo(name = "cat_name")
    var name: String?,

    @ColumnInfo(name = "cat_temperament")
    var temperament: String?,

    @ColumnInfo(name = "cat_url")
    var url: String?
) {
    @PrimaryKey(autoGenerate = true)
    var uuid: Int = 0
}