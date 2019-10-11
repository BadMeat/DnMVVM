package com.dolan.arif.dncatapp.view


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.dolan.arif.dncatapp.R
import com.dolan.arif.dncatapp.utlis.getProgressDrawable
import com.dolan.arif.dncatapp.utlis.loadImage
import com.dolan.arif.dncatapp.viewmodel.CatDetailViewModel
import kotlinx.android.synthetic.main.fragment_detail.*

class DetailFragment : Fragment() {

    private lateinit var catDetailViewModel: CatDetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            val uuid = DetailFragmentArgs.fromBundle(it).uuid
            catDetailViewModel = ViewModelProviders.of(this).get(CatDetailViewModel::class.java)
            catDetailViewModel.fetchData(uuid)
        }

        showData()

        btn_list.setOnClickListener {
            val action = DetailFragmentDirections.actionListFragment()
            Navigation.findNavController(it).navigate(action)
        }
    }

    private fun showData() {
        catDetailViewModel.cat.observe(this, Observer { cat ->
            cat?.let {
                img_poster.loadImage(it.url, getProgressDrawable(context!!))
                txt_title.text = it.name
                txt_detail.text = it.temperament
            }
        })

        catDetailViewModel.isLoading.observe(this, Observer {isLoading ->
            isLoading?.let {
                progress_main.visibility = if(it) View.VISIBLE else View.GONE
            }
        })
    }
}
