package ru.ridkeim.universalconverter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel(){
    private val _currentConverter = MutableLiveData<UniversalConverter>()
    val currentConverter : LiveData<UniversalConverter>
        get() = _currentConverter
    private val _converters = MutableLiveData<List<UniversalConverter>>()
    val converters : LiveData<List<UniversalConverter>>
        get() = _converters
    init {
        val arrayList = ArrayList<UniversalConverter>()
        val universalConverter = UniversalConverter(1.0, "В метрах")
        _currentConverter.value = universalConverter
        arrayList.add(universalConverter)
        arrayList.add(UniversalConverter(3.0,"В котанах"))
        arrayList.add(UniversalConverter(7.0,"В папугаях"))
        arrayList.add(UniversalConverter(0.5,"В удавах"))
        _converters.value = arrayList
    }

    fun onUniversalConverterClicked(item : UniversalConverter){
        _currentConverter.postValue(item)
    }
}