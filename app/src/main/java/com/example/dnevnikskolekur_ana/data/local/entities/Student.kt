package com.example.dnevnikskolekur_ana.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import java.util.*

@Entity(tableName = "students")
data class Student (
    val name : String,
    val lastName : String,
    val content : String,
    var date : Long,
    val accessEmails : List<Access>,
    val color: String,
    var answers : List<Answer> = emptyList(),
    var sumOfMarks : Int = 0,
    @Expose(deserialize = false, serialize = false)
    var isSynced: Boolean = false,
    @PrimaryKey(autoGenerate = false)
    val id : String = UUID.randomUUID().toString()
)