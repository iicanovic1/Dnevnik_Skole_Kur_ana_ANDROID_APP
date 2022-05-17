package com.example.dnevnikskolekur_ana.data.local

import androidx.room.TypeConverter
import com.example.dnevnikskolekur_ana.data.local.entities.Access
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {
    // Room ne može da smiješta listu accessa pa je potreban ovaj konverter
    @TypeConverter
    fun fromList(list: List<Access>) : String{  // iz liste stringova u JSON String
        return  Gson().toJson(list)
    }

    @TypeConverter
    fun toList(string: String) : List<Access> { // IZ JSONA izvlači listu accessa za ROOM
        return Gson().fromJson(string, object  : TypeToken<List<Access>>() {}.type) // anonimna klasa koja nasljeđuje Type TOken
    }
}