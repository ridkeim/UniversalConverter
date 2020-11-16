package ru.ridkeim.universalconverter

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import java.lang.NumberFormatException
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols


class MainViewModel : ViewModel(){
    private val TAG = MainViewModel::class.java.canonicalName
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

    private val _rawValue = MutableLiveData<Double>()

    val convertedValue : LiveData<String> = Transformations.map(_rawValue) {
        it?.let {
            val fromMeters = currentConverter.value?.convertFromRaw(it)
            val format = df.format(fromMeters)
            format
        }
    }
    init {
        val arrayList = ArrayList<UniversalConverter>()
        val universalConverter = UniversalConverter(1.0, "В милиметры")
        _currentConverter.value = universalConverter
        with(arrayList) {
            add(universalConverter)
            add(UniversalConverter(10.0,"В сантимеры"))
            add(UniversalConverter(100.0,"В дециметры"))
            add(UniversalConverter(1000.0,"В метры"))
            add(UniversalConverter(10000.0,"В километры"))
            add(UniversalConverter(25.4,"В дюймах"))
            add(UniversalConverter(304.8,"В футах"))
            add(UniversalConverter(914.4,"В ярдах"))
            add(UniversalConverter(1609344.0,"В милях"))
            add(UniversalConverter(1852000.0,"В морских милях"))
            add(UniversalConverter(711.2,"В аршинах"))
            add(UniversalConverter(2133.6,"В саженях"))
            add(UniversalConverter(1066800.0,"В верстах"))
            add(UniversalConverter(44.45,"В вершках"))
            add(UniversalConverter(177.8,"В пядях"))
            for (i in 30..50){
                add(UniversalConverter(i.toDouble(),"Item $i"))
            }
        }

        _converters.value = arrayList

        df.applyPattern("###.######")
        val decimalFormatSymbols = DecimalFormatSymbols()
        decimalFormatSymbols.decimalSeparator = '.'
        df.decimalFormatSymbols = decimalFormatSymbols

    }

    fun onUniversalConverterClicked(item : UniversalConverter){
        if(_currentConverter.value != item){
            _currentConverter.value = item
        }
        Log.d(TAG,"onUniversalConverterClicked")
    }

    fun onTextEditDone(s : String){
        if (s.isEmpty()) return
        val res : Double = try {
            s.toDouble()
        } catch (e : NumberFormatException){
            _numberFormatException.value = true
            0.0
        }
        val raw = currentConverter.value?.convertToRaw(res)
        if(_rawValue.value != raw){
            _rawValue.value = raw
            Log.d(TAG,"onTextEditDone")
        }else{
            Log.d(TAG,"onTextEditDone nothing has changed")
        }
    }

    fun onUpdateTextEdit() {
        _rawValue.value = _rawValue.value
        Log.d(TAG,"onUpdateText")
    }

    fun errorToastShown(){
        _numberFormatException.value = false
    }


    override fun onCleared() {
        Log.d(TAG,"onCleared")
        super.onCleared()

    }


}