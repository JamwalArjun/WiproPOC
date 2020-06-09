package com.test.wipropoc.ui.main

import android.graphics.Rect
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.VisibleForTesting
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


import com.test.wipropoc.R
import com.test.wipropoc.databinding.MainFragmentBinding
import com.test.wipropoc.util.Util
import kotlinx.android.synthetic.main.main_fragment.*

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var rowRecyclerViewAdapter: RowRecyclerViewAdapter
    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
     val mainViewModel: MainViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
       val mainFragmentBinding:MainFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.main_fragment,container,false)
        return mainFragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView.apply {
            this.layoutManager = LinearLayoutManager(activity)
        }
        recyclerView.addItemDecoration(
            MarginItemDecoration(
                resources.getDimension(R.dimen.row_margin).toInt()
            )
        )
        swipeContainer.setOnRefreshListener {
            if (Util.isNetworkAvailable(activity)) {
                mainViewModel.getData()
                observeLiveDataFromViewModel()
            }else{
                Toast.makeText(activity,R.string.internet_unavailable,Toast.LENGTH_LONG).show()
            }
            swipeContainer.isRefreshing = false
        }
        makeNetworkCall()
    }
    /*
    * Call the API when internet available to get data in view model
    */
    private fun makeNetworkCall() {
        if (Util.isNetworkAvailable(activity)) {
            progressIndicator.visibility = View.VISIBLE
            mainViewModel.getData()
            observeLiveDataFromViewModel()
        } else {
            recyclerView.visibility = View.GONE
            tvError.visibility = View.VISIBLE
            progressIndicator.visibility = View.GONE
            tvError.text = resources.getString(R.string.internet_unavailable)
        }
    }

    /*
    * This method observe data from live data present in view model and set to recyclerview or show error message
    */
    private fun observeLiveDataFromViewModel() {
        mainViewModel.titleLive.observe(viewLifecycleOwner, Observer { activity?.title = it })
        mainViewModel.screenStateLive.observe(viewLifecycleOwner, Observer { it ->
            progressIndicator.visibility = View.GONE
            if (it.rows.isEmpty()) {
                it.error.let {
                    recyclerView.visibility = View.GONE
                    tvError.visibility = View.VISIBLE
                    tvError.text = it
                }
            } else {
                it.rows.let {
                    recyclerView.visibility = View.VISIBLE
                    tvError.visibility = View.GONE
                    if (!this::rowRecyclerViewAdapter.isInitialized) {
                        rowRecyclerViewAdapter = RowRecyclerViewAdapter( it)
                        recyclerView.adapter = rowRecyclerViewAdapter
                    } else {
                        rowRecyclerViewAdapter.setData(it)
                    }
                }
            }
        })
    }

    /*
    * Inner class for ItemDecoration for recyclerview
    */
    class MarginItemDecoration(private val spaceHeight: Int) : RecyclerView.ItemDecoration() {
        override fun getItemOffsets(
            outRect: Rect, view: View,
            parent: RecyclerView, state: RecyclerView.State
        ) {
            with(outRect) {
                if (parent.getChildAdapterPosition(view) == 0) {
                    top = spaceHeight
                }
                bottom = spaceHeight
            }
        }
    }
}