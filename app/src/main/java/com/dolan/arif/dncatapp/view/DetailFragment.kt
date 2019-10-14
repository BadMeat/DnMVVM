package com.dolan.arif.dncatapp.view


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import com.dolan.arif.dncatapp.R
import com.dolan.arif.dncatapp.databinding.FragmentDetailBinding
import com.dolan.arif.dncatapp.viewmodel.CatDetailViewModel
import kotlinx.android.synthetic.main.fragment_detail.*

class DetailFragment : Fragment(), CatDetailListener {

    private lateinit var catDetailViewModel: CatDetailViewModel
    private lateinit var dataBinding: FragmentDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dataBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_detail,container,false)
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dataBinding.listener = this
        arguments?.let {
            val uuid = DetailFragmentArgs.fromBundle(it).uuid
            catDetailViewModel = ViewModelProviders.of(this).get(CatDetailViewModel::class.java)
            catDetailViewModel.fetchData(uuid)
        }

        showData()
    }

    override fun onBackClicked(v : View) {
        val action = DetailFragmentDirections.actionListFragment()
        Navigation.findNavController(v).navigate(action)
    }

    private fun showData() {
        catDetailViewModel.cat.observe(this, Observer { cat ->
            dataBinding.catModel = cat
        })

        catDetailViewModel.isLoading.observe(this, Observer { isLoading ->
            isLoading?.let {
                progress_main.visibility = if (it) View.VISIBLE else View.GONE
            }
        })
    }
}
