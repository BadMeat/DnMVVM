package com.dolan.arif.dncatapp.utlis

import android.content.Context
import android.widget.ImageView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dolan.arif.dncatapp.R

/**
 * Created by Bencoleng on 06/10/2019.
 */

fun getProgressDrawable(context: Context): CircularProgressDrawable {
    return CircularProgressDrawable(context).apply {
        strokeWidth = 10f
        centerRadius = 50f
        start()
    }
}

fun ImageView.loadImage(uri: String?, progressDrawable: CircularProgressDrawable) {
    val option = RequestOptions()
        .placeholder(progressDrawable)
        .error(R.drawable.cat_poster)
    Glide.with(context)
        .setDefaultRequestOptions(option)
        .load(uri)
        .into(this)
}