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
import com.example.dnevnikskolekur_ana.data.local.entities.Student
import kotlinx.android.synthetic.main.item_student.view.*
import java.text.SimpleDateFormat
import java.util.*

class StudentAdapter : RecyclerView.Adapter<StudentAdapter.StudnetViewHolder>(){

    inner class StudnetViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    private val diffCallback = object : DiffUtil.ItemCallback<Student>(){  // pronalazak razlika između 2 liste
        override fun areItemsTheSame(oldItem: Student, newItem: Student): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Student, newItem: Student): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }
    private val differ = AsyncListDiffer(this,diffCallback)

    private var onItemClickListener: ((Student) -> Unit)? = null

    var students: List<Student>
        get() = differ.currentList
        set(value) = differ.submitList(value)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudnetViewHolder {
        return StudnetViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_student, parent, false)
        )
    }

    override fun onBindViewHolder(holder: StudnetViewHolder, position: Int) {
        val student = students[position]
        holder.itemView.apply {
            tvTitle.text = student.name + " " + student.lastName
            if(!student.isSynced){
                ivSynced.setImageResource(R.drawable.ic_cross)
                tvSynced.text = "Nije sihronizovano"
            }else
            {
                ivSynced.setImageResource(R.drawable.ic_check)
                tvSynced.text = "Sihronizovano"
            }
            val dateFormat = SimpleDateFormat("dd.MM.yy, HH:mm", Locale.getDefault())
            val dateString = dateFormat.format(student.date)
            tvDate.text = dateString

            val drawable = ResourcesCompat.getDrawable(resources, R.drawable.circle_shape, null)
            drawable?.let{
                val wrappedDrawable = DrawableCompat.wrap(it)
                val color = Color.parseColor("#${student.color}")
                DrawableCompat.setTint(wrappedDrawable , color)
                viewStudentColor.background = wrappedDrawable
            }

            setOnClickListener{
                onItemClickListener?.let { click ->
                    click(student)
                }
            }

        }
    }

    override fun getItemCount(): Int {
        return students.size
    }

    public fun setOnItemClickListener(onItemClick : (Student) -> Unit) {
        this.onItemClickListener = onItemClick
    }
}