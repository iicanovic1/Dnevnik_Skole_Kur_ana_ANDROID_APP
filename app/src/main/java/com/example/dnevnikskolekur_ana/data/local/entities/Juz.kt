package com.example.dnevnikskolekur_ana.data.local.entities


data class Juz (val juzName : String, val surahs : List<Surah>) {

    override fun toString(): String {
        return juzName
    }

}
