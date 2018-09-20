package com.pjl.kyubee.history

import android.support.v7.widget.RecyclerView
import android.view.View
import com.pjl.kyubee.model.Solve
import kotlinx.android.synthetic.main.history_item.view.*

class HistoryViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    fun bind(solve: Solve?) {
        if (solve == null) {
            itemView.solve_time.text = "Loading"
        } else {
            itemView.solve_id.text = solve.id.toString()
            itemView.solve_time.text = solve.time.toString()
        }
    }
}