package com.pjl.kyubee.activity

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.pjl.kyubee.model.Session

class SessionAdapter(
        context: Context,
        private val resourceId: Int,
        private val sessions: MutableList<Session>
) : ArrayAdapter<Session>(context, resourceId, sessions) {

    private val inflater = LayoutInflater.from(context)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return prepareView(position, convertView, parent)
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return prepareView(position, convertView, parent)
    }

    fun setSessions(newSessions: List<Session>) {
        clear()
        sessions.addAll(newSessions)
        notifyDataSetChanged()
    }

    private fun prepareView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: inflater.inflate(resourceId, parent, false)
        view.findViewById<TextView>(android.R.id.text1).text = sessions[position].name
        return view
    }
}