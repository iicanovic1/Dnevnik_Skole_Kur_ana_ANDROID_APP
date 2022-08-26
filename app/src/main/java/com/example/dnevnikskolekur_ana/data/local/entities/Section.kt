package com.example.dnevnikskolekur_ana.data.local.entities


data class Section (val sectionName : String, val sectionNumber : Int, val chapters : List<Chapter>) {

    override fun toString(): String {
        return sectionName
    }

}
