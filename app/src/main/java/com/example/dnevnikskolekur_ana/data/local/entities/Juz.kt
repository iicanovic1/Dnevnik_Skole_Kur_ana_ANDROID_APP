package com.example.dnevnikskolekur_ana.data.local.entities

import com.example.dnevnikskolekur_ana.data.local.entities.Juz.*
import com.example.dnevnikskolekur_ana.data.local.entities.Surah.*

enum class Juz (val juzName : String, val surahs : List<Surah>) {
    JUZ_NULL("Odaberite Džuz", emptyList()),
    JUZ26("Džuz 26", listOf(AL_AHKAF,AD_DARIJAT,MUHAMMED)),
    JUZ27("Džuz 27", listOf(AN_NAJM,AT_TUR));

    override fun toString(): String {
        return juzName
    }

}
val juzesList = listOf(JUZ_NULL, JUZ26, JUZ27)