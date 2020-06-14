package com.test.wipropoc.ui.fragment

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
import com.test.wipropoc.model.Row
import com.test.wipropoc.ui.adapter.RowRecyclerViewAdapter
import com.test.wipropoc.ui.viewmodel.MainViewModel
import com.test.wipropoc.util.Util


class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var rowRecyclerViewAdapter: RowRecyclerViewAdapter

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    val mainViewModel: MainViewModel by viewModels()
    private lateinit var mainFragmentBinding: MainFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        mainFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.main_fragment, container, false)
        return mainFragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prepareRecyclerAndSwipeToRefresh()
        fetchJSON()
    }

    /**
     * Prepare RecyclerView and SwipeRefreshLayout
     *
     */
    private fun prepareRecyclerAndSwipeToRefresh() {
        mainFragmentBinding.recyclerView.apply {
            layoutManager = LinearLayoutManager(activity)
            addItemDecoration(
                MarginItemDecoration(
                    resources.getDimension(R.dimen.row_margin).toInt()
                )
            )
        }
        mainFragmentBinding.swipeContainer.setOnRefreshListener {
            if (Util.isNetworkAvailable(activity)) {
                mainViewModel.getDataFromNetwork()
                observeLiveDataFromViewModel()
            } else {
                Toast.makeText(activity, R.string.internet_unavailable, Toast.LENGTH_LONG).show()
            }
            mainFragmentBinding.swipeContainer.isRefreshing = false
        }
    }

    /*
    * Call the API when internet available to get data in view model otherwise show error message of internet
    *
    */
    private fun fetchJSON() {
        if (Util.isNetworkAvailable(activity)) {
            mainFragmentBinding.progressIndicator.visibility = View.VISIBLE
            mainViewModel.getDataFromNetwork()
            observeLiveDataFromViewModel()
        } else {
            hideRecyclerViewShowError(resources.getString(R.string.internet_unavailable))
        }
    }

    /*
    * This method observe data from live data present in view model and set to
    *  RecyclerView.Adapter or show error message
    */
    private fun observeLiveDataFromViewModel() {
        mainViewModel.titleLive.observe(viewLifecycleOwner, Observer { activity?.title = it })
        mainViewModel.screenStateLive.observe(viewLifecycleOwner, Observer {
            mainFragmentBinding.progressIndicator.visibility = View.GONE
            if (it.rows.isEmpty()) hideRecyclerViewShowError(it.error) else showRecyclerView(it.rows)
        })
    }

    /**
     * This method to show RecyclerView and hide error TextView
     *
     * @param rowList  This is list of row to be consumed by adapter
     */
    private fun showRecyclerView(rowList: List<Row>) {
        mainFragmentBinding.recyclerView.visibility = View.VISIBLE
        mainFragmentBinding.tvError.visibility = View.GONE
        if (!this::rowRecyclerViewAdapter.isInitialized) {
            rowRecyclerViewAdapter = RowRecyclerViewAdapter(rowList)
            mainFragmentBinding.recyclerView.adapter = rowRecyclerViewAdapter
        } else {
            rowRecyclerViewAdapter.setData(rowList)
        }
    }

    /**
     * This method hide RecyclerView and show  error TextView
     *
     * @param errorMessage The error message to be shown to user
     */
    private fun hideRecyclerViewShowError(errorMessage: String) {
        mainFragmentBinding.recyclerView.visibility = View.GONE
        mainFragmentBinding.tvError.visibility = View.VISIBLE
        mainFragmentBinding.progressIndicator.visibility = View.GONE
        mainFragmentBinding.tvError.text = errorMessage
    }

    /**
     * Inner class for ItemDecoration for recyclerview
     *
     * @property spaceHeight  The coordinate of the rectangle
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