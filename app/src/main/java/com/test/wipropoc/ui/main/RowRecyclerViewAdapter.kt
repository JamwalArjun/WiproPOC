package com.test.wipropoc.ui.main

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.test.wipropoc.R
import com.test.wipropoc.databinding.RowItemBinding
import com.test.wipropoc.model.Row

class RowRecyclerViewAdapter(rowList: List<Row>) :
    ListAdapter<Row, RowRecyclerViewAdapter.ViewHolder>(DiffCallback()) {

    private var rowList: ArrayList<Row>

    init {
        this.rowList = rowList as ArrayList<Row>
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val rowItemBinding = DataBindingUtil.inflate<RowItemBinding>(
            LayoutInflater.from(parent.context),
            R.layout.row_item,
            parent,
            false
        )
        return ViewHolder(rowItemBinding)
    }

    override fun getItemCount(): Int {
        val list = rowList.filter { row -> row.title != null }
        rowList = list as ArrayList<Row>
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.rowItemBinding.row = rowList[position]
    }

    /**
     * set data to list
     * @param rowList add to adapter
     */
    fun setData(rowList: List<Row>) {
        this.rowList = rowList as ArrayList<Row>
    }

    /**
     * View holder class of adapter  items
     * @param rowItemBinding item view
     */
    class ViewHolder(val rowItemBinding: RowItemBinding) :
        RecyclerView.ViewHolder(rowItemBinding.root)

    /**
     * DiffUtil compare the item views
     */
    class DiffCallback : DiffUtil.ItemCallback<Row>() {
        override fun areItemsTheSame(oldItem: Row, newItem: Row): Boolean {
            return true
        }

        override fun areContentsTheSame(oldItem: Row, newItem: Row): Boolean {
            return oldItem.title.equals(
                newItem.title,
                false
            ) && oldItem.description.equals(newItem.description, false) &&
                    oldItem.imageHref.equals(newItem.imageHref, false)
        }

    }
}