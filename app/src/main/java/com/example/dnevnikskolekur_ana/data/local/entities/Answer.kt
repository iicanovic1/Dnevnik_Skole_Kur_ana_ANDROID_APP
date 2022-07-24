package com.example.dnevnikskolekur_ana.data.local.entities

data class Answer(
    val type: AnswerType,
    val juzNumber: Juz,
    val surah: Surah?,
    val ajeh : Int?,
    val date: Long,
    val mark: Int
)
