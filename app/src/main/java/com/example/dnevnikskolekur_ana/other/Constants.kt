package com.example.dnevnikskolekur_ana.other

import com.example.dnevnikskolekur_ana.data.local.entities.AnswerType
import com.example.dnevnikskolekur_ana.data.local.entities.Section
import com.example.dnevnikskolekur_ana.data.local.entities.Chapter

object Constants {
    val IGNORE_AUTH_URLS= listOf("/login","/register") // lista urlova koji se ne presreću za zaglavlje o autorizaciji

    const val DATABASE_NAME = "students_db"

    const val BASE_URL = "https://192.168.1.7:8002" // Retrofit spajanje na server

    const val ENCRYPTED_SHARED_PREF_NAME = "en_shared_pref"
    const val KEY_LOGGED_IN_EMAIL = "KEY_LOGGED_IN_EMAIL"
    const val KEY_PASSWORD = "KEY_PASSWORD"
    const val NO_EMAIL = "NO_EMAIL"
    const val NO_PASSWORD = "NO_PASSWORD"

    const val DEFAULT_STUDENT_COLOR = "FFA500"

    val MARKS = List(5,{1+it})

    val Chapter_NULL = Chapter("Odaberite poglavlje",null)
    val AL_FATIHA = Chapter("1.AL-FATIHA",7)
    val AL_BEKARA = Chapter("2.AL-BEKARA",286)
    val ALI_IMRAN = Chapter("3.ALI-IMRAN",200)
    val AN_NISA = Chapter("4.AN-NISA",176)
    val AL_MAIDA = Chapter("5.AL-MAIDA",120)
    val AL_ANAM = Chapter("6.AL-ANAM",165)
    val AL_ARAF = Chapter("7.AL-ARAF",206)
    val AL_ENFAL = Chapter("8.AL-ENFAL",75)
    val AT_TEVBA = Chapter("9.AT-TEVBA",129)
    val JUNUS = Chapter("10.JUNUS",109)
    val HUD = Chapter("11.HUD",123)
    val JUSUF = Chapter("12.JUSUF",111)
    val AR_RAD = Chapter("13.AR-RAD",43)
    val IBRAHIM = Chapter("14.IBRAHIM",52)
    val AL_HIJR = Chapter("15.AL-HIDŽR",99)
    val AN_NAHL = Chapter("16.AN-NAHL",128)
    val AL_ISRA = Chapter("17.AL-ISRA",111)
    val AL_KEHF = Chapter("18.AL-KEHF",110)
    val MERJEM = Chapter("19.MERJEM",98)

    val Section_NULL = Section("Odaberite cjelinu",0, emptyList())
    val Section1 = Section("Cjelina 1",1, listOf(AL_FATIHA,AL_BEKARA))
    val Section2 = Section("Cjelina 2",2, listOf(AL_BEKARA))
    val Section3 = Section("Cjelina 3",3, listOf(ALI_IMRAN))
    val Section4 = Section("Cjelina 4",4, listOf(AN_NISA))
    val Section5 = Section("Cjelina 5",5, listOf(AN_NISA))
    val Section6 = Section("Cjelina 6",6, listOf(AL_MAIDA))
    val Section7 = Section("Cjelina 7",7, listOf(AL_ANAM))
    val Section8 = Section("Cjelina 8",8, listOf(AL_ARAF))
    val Section9 = Section("Cjelina 9",9, listOf(AL_ENFAL))
    val Section10 = Section("Cjelina 10",10, listOf(AT_TEVBA))
    val Section11 = Section("Cjelina 11",11, listOf(JUNUS,HUD))
    val Section12 = Section("Cjelina 12",12, listOf(JUSUF))
    val Section13 = Section("Cjelina 13",13, listOf(AR_RAD, IBRAHIM))
    val Section14 = Section("Cjelina 14",14, listOf(AL_HIJR, AN_NAHL))
    val Section15 = Section("Cjelina 15",15, listOf(AL_ISRA, AL_KEHF))
    val Section16 = Section("Cjelina 16",16, listOf(MERJEM))

    val SECTIONS = listOf(Section_NULL, Section1, Section2,Section3,Section4,Section5,Section6,
        Section7,Section8,Section9,Section10,Section11,Section12,Section13,Section14,Section15,Section16)


    val TYPE_NULL = AnswerType("Svi odgovori")
    val CHAPTER = AnswerType("Poglavlje")
    val SECTION = AnswerType("Cjelina")
    val SENTENCE = AnswerType("Rečenica")

    val ANSWER_TYPES = listOf(
        TYPE_NULL,
        SECTION,
        CHAPTER,
        SENTENCE
    )






}