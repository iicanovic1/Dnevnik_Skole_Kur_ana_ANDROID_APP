package com.example.dnevnikskolekur_ana.data.local.entities

data class Chapter (var chapterName : String, val numberOfSentences : Int?) {

    override fun toString(): String {
        return chapterName
    }
}