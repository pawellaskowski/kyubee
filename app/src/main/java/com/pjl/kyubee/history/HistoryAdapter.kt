package com.pjl.kyubee.history

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.pjl.kyubee.R
import com.pjl.kyubee.entity.Solve
import kotlinx.android.synthetic.main.history_item.view.*

class HistoryAdapter : RecyclerView.Adapter<HistoryAdapter.SolveViewHolder>() {

    private var solves: List<Solve>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SolveViewHolder {
        val itemView = LayoutInflater
                .from(parent.context)
                .inflate(R.layout.history_item, parent, false)
        return SolveViewHolder(itemView)
    }

    override fun getItemCount(): Int = solves?.size ?: 0

    override fun onBindViewHolder(holder: SolveViewHolder, position: Int) {
        if (solves != null) {
            val current = solves?.get(position)
            holder.itemView.solve_id.text = current?.id.toString()
            holder.itemView.solve_time.text = current?.time.toString()
        } else {
            holder.itemView.solve_time.text = "No solve"
        }
    }

    fun setSolves(solves: List<Solve>?) {
        this.solves = solves
        notifyDataSetChanged()
    }

    class SolveViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}