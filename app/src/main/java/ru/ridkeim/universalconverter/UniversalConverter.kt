package ru.ridkeim.universalconverter

data class UniversalConverter(val ratio : Double, val title : String){

    fun toMeters(value : Double) : Double{
        return value/ratio
    }
    fun fromMeters(value : Double) : Double{
        return value*ratio
    }
}