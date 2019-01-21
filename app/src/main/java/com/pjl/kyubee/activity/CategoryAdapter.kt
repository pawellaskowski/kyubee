package com.pjl.kyubee.activity

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.pjl.kyubee.model.Category

class CategoryAdapter(
        context: Context,
        private val resourceId: Int,
        private val categories: MutableList<Category>
) : ArrayAdapter<Category>(context, resourceId, categories) {

    private val inflater = LayoutInflater.from(context)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return prepareView(position, convertView, parent)
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return prepareView(position, convertView, parent)
    }

    fun setCategories(newCategories: List<Category>?) {
        clear()
        if (newCategories != null) {
            categories.addAll(newCategories)
        }
        notifyDataSetChanged()
    }

    private fun prepareView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: inflater.inflate(resourceId, parent, false)
        view.findViewById<TextView>(android.R.id.text1).text = categories[position].name
        return view
    }
}