package com.example.dnevnikskolekur_ana.data.local.entities

import java.util.*

data class Answer(
    val type: AnswerType,
    val section: Section,
    val chapter: Chapter,
    val sentenceMin : Int?,
    val sentenceMax : Int?,
    val date: Long,
    val mark: Int,
    val id: String = UUID.randomUUID().toString(),
    val revision : Boolean = false
)
