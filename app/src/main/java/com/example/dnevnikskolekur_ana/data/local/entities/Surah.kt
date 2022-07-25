package com.example.dnevnikskolekur_ana.data.local.entities

enum class Surah (var surahName : String, val numberOfAjat : Int?) {
    SURAH_NULL("Odgovor bez sure",null),
    AL_FATIHA("1.AL-FATIHA",7),
    AL_BEKARA("2.AL-BEKARA",286),
    ALI_IMRAN("3.ALI-IMRAN",200),
    AN_NISA("4.AN-NISA",176),
    AL_MAIDA("5.AL-MAIDA",120),
    AL_ANAM("6.AL-ANAM",165),
    AL_ARAF("7.AL-ARAF",206),
    AL_ENFAL("8.AL-ENFAL",75),
    AT_TEVBA("9.AT-TEVBA",129),
    JUNUS("10.JUNUS",109),
    HUD("11.HUD",123),
    JUSUF("12.JUSUF",111),
    AR_RAD("13.AR-RAD",43),
    IBRAHIM("14.IBRAHIM",52),
    AL_HIJR("15.AL-HIDŽR",99),
    AN_NAHL("16.AN-NAHL",128),
    AL_ISRA("17.AL-ISRA",111),
    AL_KEHF("18.AL-KEHF",110),
    MERJEM("19.MERJEM",98);

    override fun toString(): String {
        return surahName
    }
}