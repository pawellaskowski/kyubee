package com.pjl.kyubee.history

import android.arch.paging.PagedListAdapter
import android.support.v7.util.DiffUtil
import android.view.LayoutInflater
import android.view.ViewGroup
import com.pjl.kyubee.R
import com.pjl.kyubee.model.Solve

class HistoryAdapter : PagedListAdapter<Solve, HistoryViewHolder>(SOLVE_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val itemView = LayoutInflater
                .from(parent.context)
                .inflate(R.layout.history_item, parent, false)
        return HistoryViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        val solve = getItem(position)
        if (solve != null) {
            holder.bind(solve)
        }
    }

    companion object {
        private val SOLVE_COMPARATOR = object : DiffUtil.ItemCallback<Solve>() {
            override fun areContentsTheSame(oldItem: Solve, newItem: Solve): Boolean =
                    oldItem == newItem

            override fun areItemsTheSame(oldItem: Solve, newItem: Solve): Boolean =
                    oldItem.id == newItem.id
        }
    }
}