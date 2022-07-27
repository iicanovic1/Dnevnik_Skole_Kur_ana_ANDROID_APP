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
import com.example.dnevnikskolekur_ana.data.local.entities.AnswerType
import com.example.dnevnikskolekur_ana.data.local.entities.Student
import com.example.dnevnikskolekur_ana.other.Constants
import com.example.dnevnikskolekur_ana.other.Constants.AJEH
import com.example.dnevnikskolekur_ana.other.Constants.JUZ
import kotlinx.android.synthetic.main.item_answer.view.*
import kotlinx.android.synthetic.main.item_student.view.*
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
            if (answer.type == JUZ )
                name = answer.juzNumber.toString()
            else
                name = answer.surah.toString()

            if (answer.type == AJEH )
                name += " ${answer.ajehMin}-${answer.ajehMax}"

            tvTitle.text = name
            tvMark.text = answer.mark.toString()
            val dateFormat = SimpleDateFormat("dd.MM.yy, HH:mm", Locale.getDefault())
            val dateString = dateFormat.format(answer.date)
            tvDate.text = dateString

            val drawable = ResourcesCompat.getDrawable(resources, R.drawable.circle_shape, null)
            drawable?.let{
                val wrappedDrawable = DrawableCompat.wrap(it)
                val color = Color.parseColor("#${Constants.DEFAULT_NOTE_COLOR}")
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