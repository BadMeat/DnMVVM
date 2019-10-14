package com.dolan.arif.dncatapp.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.dolan.arif.dncatapp.R
import com.dolan.arif.dncatapp.databinding.ItemCatBinding
import com.dolan.arif.dncatapp.model.Cat
import kotlinx.android.synthetic.main.item_cat.view.*

/**
 * Created by Bencoleng on 06/10/2019.
 */
class CatAdapter : RecyclerView.Adapter<CatAdapter.CatHolder>(), CatListener {

    private val catList = mutableListOf<Cat>()

    fun setCatList(e: List<Cat>) {
        catList.clear()
        catList.addAll(e)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatHolder {
        val layout = LayoutInflater.from(parent.context)
        val view = DataBindingUtil.inflate<ItemCatBinding>(layout, R.layout.item_cat, parent, false)
        return CatHolder(view)
    }

    override fun getItemCount() = catList.size

    override fun onBindViewHolder(holder: CatHolder, position: Int) {
        holder.view.cat = catList[position]
        holder.view.listener = this
    }

    override fun onCatItemClicked(v: View) {
        val action = ListFragmentDirections.actionDetailFragment()
        action.uuid = v.txt_id.text.toString().toInt()
        Navigation.findNavController(v).navigate(action)
    }


    class CatHolder(var view: ItemCatBinding) : RecyclerView.ViewHolder(view.root)
}