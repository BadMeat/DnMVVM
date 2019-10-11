package com.dolan.arif.dncatapp.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.dolan.arif.dncatapp.R
import com.dolan.arif.dncatapp.model.Cat
import com.dolan.arif.dncatapp.utlis.getProgressDrawable
import com.dolan.arif.dncatapp.utlis.loadImage
import kotlinx.android.synthetic.main.item_cat.view.*

/**
 * Created by Bencoleng on 06/10/2019.
 */
class CatAdapter : RecyclerView.Adapter<CatAdapter.CatHolder>() {

    private val catList = mutableListOf<Cat>()

    fun setCatList(e: List<Cat>) {
        catList.clear()
        catList.addAll(e)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatHolder {
        val layout = LayoutInflater.from(parent.context)
        val view = layout.inflate(R.layout.item_cat, parent, false)
        return CatHolder(view)
    }

    override fun getItemCount() = catList.size

    override fun onBindViewHolder(holder: CatHolder, position: Int) {
        holder.view.txt_title.text = catList[position].name
        holder.view.txt_desc.text = catList[position].temperament
        holder.view.img_poster.loadImage(
            catList[position].url,
            getProgressDrawable(holder.view.img_poster.context)
        )
        holder.view.setOnClickListener {
            val action = ListFragmentDirections.actionDetailFragment()
            action.uuid = catList[position].uuid
            Navigation.findNavController(it).navigate(action)
        }
    }

    class CatHolder(var view: View) : RecyclerView.ViewHolder(view)
}