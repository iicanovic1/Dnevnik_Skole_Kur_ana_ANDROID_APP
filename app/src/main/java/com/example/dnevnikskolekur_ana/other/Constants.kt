package com.example.dnevnikskolekur_ana.other

import com.example.dnevnikskolekur_ana.data.local.entities.Juz.*
import com.example.dnevnikskolekur_ana.data.local.entities.juzesList

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

    val JUZES = juzesList


}