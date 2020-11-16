package ru.ridkeim.universalconverter

data class UniversalConverter(val ratio : Double, val title : String){

    fun convertToRaw(value : Double) : Double{
        return value*ratio
    }
    fun convertFromRaw(value : Double) : Double{
        return value/ratio
    }
}