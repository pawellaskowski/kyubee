package com.pjl.kyubee.history


import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.pjl.kyubee.R
import kotlinx.android.synthetic.main.fragment_history.view.*

class HistoryFragment : Fragment() {

    private lateinit var viewModel: HistoryViewModel
    private lateinit var adapter: HistoryAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_history, container, false)
        view.solve_list.adapter = adapter
        view.solve_list.layoutManager = LinearLayoutManager(activity!!)
        return view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter = HistoryAdapter()
        viewModel = ViewModelProviders.of(activity!!).get(HistoryViewModel::class.java)
        viewModel.getAllSolves().observe(this, Observer {
            adapter.submitList(it)
        })
    }

}
