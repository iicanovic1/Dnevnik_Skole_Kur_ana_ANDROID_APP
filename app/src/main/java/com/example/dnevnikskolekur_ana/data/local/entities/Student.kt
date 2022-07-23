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
    val date : Long,
    val accessEmails : List<Access>,
    val color: String,
    val answers : List<Answer> = emptyList(),
    val average : Float = 5F,
    @Expose(deserialize = false, serialize = false)
    var isSynced: Boolean = false,
    @PrimaryKey(autoGenerate = false)
    val id : String = UUID.randomUUID().toString()
)