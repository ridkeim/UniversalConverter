package ru.ridkeim.universalconverter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import java.lang.NumberFormatException
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols


class MainViewModel : ViewModel(){
    val df = DecimalFormat()
    private val _currentConverter = MutableLiveData<UniversalConverter>()
    val currentConverter : LiveData<UniversalConverter>
        get() = _currentConverter
    private val _converters = MutableLiveData<List<UniversalConverter>>()
    val converters : LiveData<List<UniversalConverter>>
        get() = _converters

    private val _numberFormatException = MutableLiveData<Boolean>(false)
    val numberFormatException : LiveData<Boolean>
        get() = _numberFormatException

    private val _meters = MutableLiveData<Double>()

    val metersString : LiveData<String> = Transformations.map(_meters) {
        it?.let {
            val fromMeters = currentConverter.value?.fromMeters(it)
            val format = df.format(fromMeters)
            format
        }
    }
    init {
        val arrayList = ArrayList<UniversalConverter>()
        val universalConverter = UniversalConverter(1.0, "В метры")
        _currentConverter.value = universalConverter
        with(arrayList) {
            add(universalConverter)
            add(UniversalConverter(10.0,"В дециметры"))
            add(UniversalConverter(100.0,"В сантимеры"))
            add(UniversalConverter(1000.0,"В милиметры"))
            add(UniversalConverter(39.3701,"В дюймах"))
            add(UniversalConverter(3.28084,"В футах"))
            add(UniversalConverter(1.09361,"В ярдах"))
            add(UniversalConverter(0.000621371,"В милях"))
            add(UniversalConverter(0.000539957,"В морских милях"))
        }

        _converters.value = arrayList

        df.applyPattern("###.######")
        val decimalFormatSymbols = DecimalFormatSymbols()
        decimalFormatSymbols.decimalSeparator = '.'
        df.decimalFormatSymbols = decimalFormatSymbols

    }

    fun onUniversalConverterClicked(item : UniversalConverter){
        _currentConverter.postValue(item)
    }

    fun onTextEditDone(s : String){
        val res : Double = try {
            s.replace(',','.').toDouble()
        } catch (e : NumberFormatException){
            _numberFormatException.postValue(true)
            0.0
        }
        _meters.postValue(currentConverter.value?.toMeters(res))
    }

    fun onUpdateTextEdit() {
        _meters.postValue(_meters.value)
    }

    fun errorToastShown(){
        _numberFormatException.postValue(false)
    }





}