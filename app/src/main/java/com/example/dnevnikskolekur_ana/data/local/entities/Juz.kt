package com.example.dnevnikskolekur_ana.data.local.entities

import com.example.dnevnikskolekur_ana.data.local.entities.Juz.*
import com.example.dnevnikskolekur_ana.data.local.entities.Surah.*

enum class Juz (val juzName : String, val surahs : List<Surah>) {
    JUZ_NULL("Odaberite Džuz", emptyList()),
    JUZ1("Džuz 1", listOf(AL_FATIHA,AL_BEKARA)),
    JUZ2("Džuz 2", listOf(AL_BEKARA)),
    JUZ3("Džuz 3", listOf(ALI_IMRAN)),
    JUZ4("Džuz 4", listOf(AN_NISA)),
    JUZ5("Džuz 5", listOf(AN_NISA)),
    JUZ6("Džuz 6", listOf(AL_MAIDA)),
    JUZ7("Džuz 7", listOf(AL_ANAM)),
    JUZ8("Džuz 8", listOf(AL_ARAF)),
    JUZ9("Džuz 9", listOf(AL_ENFAL)),
    JUZ10("Džuz 10", listOf(AT_TEVBA)),
    JUZ11("Džuz 11", listOf(JUNUS,HUD)),
    JUZ12("Džuz 12", listOf(JUSUF)),
    JUZ13("Džuz 13", listOf(AR_RAD, IBRAHIM)),
    JUZ14("Džuz 14", listOf(AL_HIJR, AN_NAHL)),
    JUZ15("Džuz 15", listOf(AL_ISRA, AL_KEHF)),
    JUZ16("Džuz 16", listOf(MERJEM));

    override fun toString(): String {
        return juzName
    }

}
val juzesList = listOf(JUZ_NULL, JUZ1, JUZ2,JUZ3,JUZ4,JUZ5,JUZ6,
    JUZ7,JUZ8,JUZ9,JUZ10,JUZ11,JUZ12,JUZ13,JUZ14,JUZ15,JUZ16)