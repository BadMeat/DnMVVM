package com.dolan.arif.dncatapp.view


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.dolan.arif.dncatapp.R
import com.dolan.arif.dncatapp.viewmodel.CatViewModel
import kotlinx.android.synthetic.main.fragment_list.*

class ListFragment : Fragment() {

    private lateinit var catViewModel: CatViewModel
    private val catAdapter = CatAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        catViewModel = ViewModelProviders.of(this).get(CatViewModel::class.java)
        catViewModel.refresh()

        rv_main.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = catAdapter
        }

        rf_main.setOnRefreshListener {
            progress_bar.visibility = View.VISIBLE
            rv_main.visibility = View.GONE
            txt_error.visibility = View.GONE
            rf_main.isRefreshing = false
            catViewModel.byPassRefresh()
        }

        showData()
    }

    private fun showData() {
        catViewModel.catList.observe(this, Observer { cat ->
            cat?.let {
                rv_main.visibility = View.VISIBLE
                catAdapter.setCatList(it)
            }
        })

        catViewModel.isError.observe(this, Observer { error ->
            error?.let {
                txt_error.visibility = if (it) View.VISIBLE else View.GONE
            }
        })

        catViewModel.isLoading.observe(this, Observer { load ->
            load?.let {
                progress_bar.visibility = if (it) View.VISIBLE else View.GONE
                if (it) {
                    txt_error.visibility = View.GONE
                    rv_main.visibility = View.GONE
                }
            }
        })
    }
}
