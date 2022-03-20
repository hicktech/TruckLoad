package com.github.jw3.tl.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.github.jw3.tl.R
import com.github.jw3.tl.db.Load
import java.time.LocalDateTime
import java.time.ZoneOffset

class RecyclerViewAdapter: PagingDataAdapter<Load, RecyclerViewAdapter.LoadListViewHolder>(
    DiffItemCallback()
) {
    override fun onBindViewHolder(holder: LoadListViewHolder, position: Int) {
        holder.bind(getItem(position)!!)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): LoadListViewHolder {
        return LoadListViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.layout_recycler_row, parent, false)
        )
    }

    class LoadListViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val row: TextView = view.findViewById(R.id.load_list_row)

        // todo;; revisit
        fun bind(load: Load) {
            val t = LocalDateTime.ofEpochSecond(load.loaded, 0, ZoneOffset.UTC)
            row.text = "${load.driver} - ${load.bushelEstimate} - $t"
        }
    }

    // todo;; revisit
    class  DiffItemCallback: DiffUtil.ItemCallback<Load>() {
        override fun areItemsTheSame(lhs: Load, rhs: Load): Boolean {
            return lhs.loadTime == rhs.loadTime
        }

        override fun areContentsTheSame(lhs: Load, rhs: Load): Boolean {
            return lhs.loadTime == rhs.loadTime && lhs.bushelEstimate == rhs.bushelEstimate
        }

    }
}
