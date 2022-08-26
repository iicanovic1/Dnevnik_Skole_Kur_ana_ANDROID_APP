package com.example.dnevnikskolekur_ana.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.dnevnikskolekur_ana.R
import com.example.dnevnikskolekur_ana.data.local.entities.Answer
import com.example.dnevnikskolekur_ana.other.Constants.SENTENCE
import com.example.dnevnikskolekur_ana.other.Constants.SECTION
import com.example.dnevnikskolekur_ana.other.greenColorMaker
import com.example.dnevnikskolekur_ana.other.redColorMaker
import kotlinx.android.synthetic.main.item_answer.view.*
import kotlinx.android.synthetic.main.item_student.view.tvDate
import kotlinx.android.synthetic.main.item_student.view.tvTitle
import kotlinx.android.synthetic.main.item_student.view.viewStudentColor
import java.text.SimpleDateFormat
import java.util.*

class AnswerAdapter : RecyclerView.Adapter<AnswerAdapter.AnswerViewHolder>(){

    inner class AnswerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    private val diffCallback = object : DiffUtil.ItemCallback<Answer>(){  // pronalazak razlika između 2 liste
        override fun areItemsTheSame(oldItem: Answer, newItem: Answer): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Answer, newItem: Answer): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }
    private val differ = AsyncListDiffer(this,diffCallback)

    private var onItemClickListener: ((Answer) -> Unit)? = null

    var answers: List<Answer>
        get() = differ.currentList
        set(value) = differ.submitList(value)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnswerViewHolder {
        return AnswerViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_answer, parent, false)
        )
    }

    override fun onBindViewHolder(holder: AnswerViewHolder, position: Int) {
        val answer = answers[position]
        holder.itemView.apply {
            var name : String
            if (answer.type == SECTION )
                name = answer.section.toString()
            else
                name = answer.chapter.toString()

            if (answer.type == SENTENCE )
                name += " ${answer.sentenceMin}-${answer.sentenceMax}"

            tvTitle.text = name
            tvMark.text = answer.mark.toString()
            val dateFormat = SimpleDateFormat("dd.MM.yy, HH:mm", Locale.getDefault())
            val dateString = dateFormat.format(answer.date)
            tvDate.text = dateString

            if(answer.revision)
                ivRevision.setImageResource(R.drawable.ic_double_check)
            else
                ivRevision.setImageResource(R.drawable.ic_check)


            val drawable = ResourcesCompat.getDrawable(resources, R.drawable.ic_assignment, null)
            drawable?.let{
                val wrappedDrawable = DrawableCompat.wrap(it)
                val color = Color.rgb(redColorMaker(answer.mark.toFloat()), greenColorMaker(answer.mark.toFloat()),0)
                DrawableCompat.setTint(wrappedDrawable , color)
                viewStudentColor.background = wrappedDrawable
            }

            setOnClickListener{
                onItemClickListener?.let { click ->
                    click(answer)
                }
            }

        }
    }

    override fun getItemCount(): Int {
        return answers.size
    }

    public fun setOnItemClickListener(onItemClick : (Answer) -> Unit) {
        this.onItemClickListener = onItemClick
    }
}