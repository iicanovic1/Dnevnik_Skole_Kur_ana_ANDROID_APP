package com.example.dnevnikskolekur_ana.data.remote.requests

import com.example.dnevnikskolekur_ana.data.local.entities.Answer

data class AddAnswerRequest (
    val studentID : String,
    val answer: Answer
        )