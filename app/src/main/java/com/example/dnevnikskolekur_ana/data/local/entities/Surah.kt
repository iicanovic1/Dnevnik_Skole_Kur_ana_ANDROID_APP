package com.example.dnevnikskolekur_ana.data.local.entities

enum class Surah (var surahName : String, val numberOfAjat : Int?) {
    SURAH_NULL("Odgovor bez sure",null),
    AL_AHKAF("AL-AHKAF",35),
    MUHAMMED("MUHAMMED",38),
    AD_DARIJAT("AD-DARIJAT",60),
    AT_TUR("AT-TUR",49),
    AN_NAJM("AN-NAJM",62);

    override fun toString(): String {
        return surahName
    }
}