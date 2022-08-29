package com.example.dnevnikskolekur_ana.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.example.dnevnikskolekur_ana.R
import com.example.dnevnikskolekur_ana.data.local.entities.AnswerType
import kotlinx.android.synthetic.main.spinner_item.view.*

class AnswerTypeSpinnerAdapter(context: Context, answerTypeList: List<AnswerType>) : ArrayAdapter<AnswerType>(context, 0, answerTypeList) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return initView(position, convertView, parent)
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return initView(position, convertView, parent)
    }

    private fun initView(position: Int, convertView: View?, parent: ViewGroup): View {

        val type = getItem(position)

        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.spinner_item, parent, false)
        view.name.text = type?.typeName

        return view
    }
}