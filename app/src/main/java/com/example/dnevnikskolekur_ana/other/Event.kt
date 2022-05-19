package com.example.dnevnikskolekur_ana.other

open class Event<out T> (private val content : T){

    var hasBeenHandled : Boolean = false
    private set // hasBeenHandled se može mjenjati unutar klase ali ne van nje

    fun getContentIfNotHandled() = if (hasBeenHandled){
        null
    }else{
        hasBeenHandled = true
        content
    }

    fun peekContent() = content
}