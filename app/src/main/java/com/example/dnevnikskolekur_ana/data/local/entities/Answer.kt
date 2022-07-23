package com.example.dnevnikskolekur_ana.data.local.entities

data class Answer(
    val type: AnswerType,
    val juzNumber: Juzes,
    val surah: Surahs?,
    val ajeh : Int?,
    val date: Long,
    val mark: Int
)
