package com.example.dnevnikskolekur_ana.data.local.entities

import java.util.*

data class Answer(
    val type: AnswerType,
    val juzNumber: Juz,
    val surah: Surah,
    val ajehMin : Int?,
    val ajehMax : Int?,
    val date: Long,
    val mark: Int,
    val id: String = UUID.randomUUID().toString()
)
