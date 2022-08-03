package com.example.dnevnikskolekur_ana.other

fun redColorMaker (average: Float) : Int {
    var red : Int = 255
    if(average >= 3){
        red = 255 - (((average - 3) * 255 ) / 2).toInt()
    }
    return red
}

fun greenColorMaker (average: Float) : Int {
    var green : Int = 255
    if(average <= 3){
        green = (((average - 1) * 255 ) / 2).toInt()
    }
    return green
}