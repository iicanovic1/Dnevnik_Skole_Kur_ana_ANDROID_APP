package com.example.dnevnikskolekur_ana.other

import com.example.dnevnikskolekur_ana.data.local.entities.AnswerType
import com.example.dnevnikskolekur_ana.data.local.entities.Juz
import com.example.dnevnikskolekur_ana.data.local.entities.Surah

object Constants {
    val IGNORE_AUTH_URLS= listOf("/login","/register") // lista urlova koji se ne presreću za zaglavlje o autorizaciji

    const val DATABASE_NAME = "students_db"

    const val BASE_URL = "https://192.168.1.6:8002" // Retrofit spajanje na server

    const val ENCRYPTED_SHARED_PREF_NAME = "en_shared_pref"
    const val KEY_LOGGED_IN_EMAIL = "KEY_LOGGED_IN_EMAIL"
    const val KEY_PASSWORD = "KEY_PASSWORD"
    const val NO_EMAIL = "NO_EMAIL"
    const val NO_PASSWORD = "NO_PASSWORD"

    const val DEFAULT_NOTE_COLOR = "FFA500"

    val MARKS = List(5,{1+it})

    val SURAH_NULL = Surah("Odgovor bez sure",0,null)
    val AL_FATIHA = Surah("1.AL-FATIHA",1,7)
    val AL_BEKARA = Surah("2.AL-BEKARA",2,286)
    val ALI_IMRAN = Surah("3.ALI-IMRAN",3,200)
    val AN_NISA = Surah("4.AN-NISA",4,176)
    val AL_MAIDA = Surah("5.AL-MAIDA",5,120)
    val AL_ANAM = Surah("6.AL-ANAM",6,165)
    val AL_ARAF = Surah("7.AL-ARAF",7,206)
    val AL_ENFAL = Surah("8.AL-ENFAL",8,75)
    val AT_TEVBA = Surah("9.AT-TEVBA",9,129)
    val JUNUS = Surah("10.JUNUS",10,109)
    val HUD = Surah("11.HUD",11,123)
    val JUSUF = Surah("12.JUSUF",12,111)
    val AR_RAD = Surah("13.AR-RAD",13,43)
    val IBRAHIM = Surah("14.IBRAHIM",14,52)
    val AL_HIJR = Surah("15.AL-HIDŽR",15,99)
    val AN_NAHL = Surah("16.AN-NAHL",16,128)
    val AL_ISRA = Surah("17.AL-ISRA",17,111)
    val AL_KEHF = Surah("18.AL-KEHF",18,110)
    val MERJEM = Surah("19.MERJEM",19,98)

    val JUZ_NULL = Juz("Odaberite Džuz",0, emptyList())
    val JUZ1 = Juz("Džuz 1",1, listOf(AL_FATIHA,AL_BEKARA))
    val JUZ2 = Juz("Džuz 2",2, listOf(AL_BEKARA))
    val JUZ3 = Juz("Džuz 3",3, listOf(ALI_IMRAN))
    val JUZ4 = Juz("Džuz 4",4, listOf(AN_NISA))
    val JUZ5 = Juz("Džuz 5",5, listOf(AN_NISA))
    val JUZ6 = Juz("Džuz 6",6, listOf(AL_MAIDA))
    val JUZ7 = Juz("Džuz 7",7, listOf(AL_ANAM))
    val JUZ8 = Juz("Džuz 8",8, listOf(AL_ARAF))
    val JUZ9 = Juz("Džuz 9",9, listOf(AL_ENFAL))
    val JUZ10 = Juz("Džuz 10",10, listOf(AT_TEVBA))
    val JUZ11 = Juz("Džuz 11",11, listOf(JUNUS,HUD))
    val JUZ12 = Juz("Džuz 12",12, listOf(JUSUF))
    val JUZ13 = Juz("Džuz 13",13, listOf(AR_RAD, IBRAHIM))
    val JUZ14 = Juz("Džuz 14",14, listOf(AL_HIJR, AN_NAHL))
    val JUZ15 = Juz("Džuz 15",15, listOf(AL_ISRA, AL_KEHF))
    val JUZ16 = Juz("Džuz 16",16, listOf(MERJEM))

    val JUZES = listOf(JUZ_NULL, JUZ1, JUZ2,JUZ3,JUZ4,JUZ5,JUZ6,
        JUZ7,JUZ8,JUZ9,JUZ10,JUZ11,JUZ12,JUZ13,JUZ14,JUZ15,JUZ16)


    val TYPE_NULL = AnswerType("Svi odgovori")
    val SURAH = AnswerType("Sura")
    val JUZ = AnswerType("Džuz")
    val AJEH = AnswerType("Ajet")

    val ANSWER_TYPES = listOf(
        TYPE_NULL,
        JUZ,
        SURAH,
        AJEH
    )






}