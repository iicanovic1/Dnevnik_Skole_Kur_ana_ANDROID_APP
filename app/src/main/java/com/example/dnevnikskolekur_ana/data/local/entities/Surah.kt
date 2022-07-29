package com.example.dnevnikskolekur_ana.data.local.entities

data class Surah (var surahName : String, var surahNumber : Int, val numberOfAjat : Int?) {

    override fun toString(): String {
        return surahName
    }
}