package ru.ridkeim.universalconverter

import android.graphics.Rect
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.*
import android.widget.AbsListView.MultiChoiceModeListener
import android.widget.AdapterView.OnItemSelectedListener
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.ridkeim.universalconverter.R
import com.ridkeim.universalconverter.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding : ActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        val mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        binding.mainViewModel = mainViewModel
        val universalConverterAdapter = UniversalConverterAdapter()

        binding.listConverter.adapter = universalConverterAdapter

        listConverter.setOnItemClickListener { parent, view, position, id ->
            val item = universalConverterAdapter.getItem(position)
            mainViewModel.onUniversalConverterClicked(item)
        }

        mainViewModel.currentConverter.observe(this) {
            it?.let {
                mainViewModel.onUpdateTextEdit()
            }
        }

        mainViewModel.converters.observe(this) {
            it?.let {
                universalConverterAdapter.submitList(it)
            }
        }

        mainViewModel.numberFormatException.observe(this){
             if(true == it){
                 Toast.makeText(this, getString(R.string.wrong_number), Toast.LENGTH_SHORT).show()
                 mainViewModel.errorToastShown()
             }
        }

        binding.editTextNumberDecimal.onFocusChangeListener = View.OnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) {
                val inputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.hideSoftInputFromWindow(v.windowToken, 0)
                mainViewModel.onTextEditDone(binding.editTextNumberDecimal.text.toString())
            }
        }

        binding.editTextNumberDecimal.setOnEditorActionListener { v, actionId, event ->
            return@setOnEditorActionListener when (actionId){
                EditorInfo.IME_ACTION_DONE -> {
                    v.clearFocus()
                    true
                }
                else -> false
            }
        }

        binding.lifecycleOwner = this
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        ev?.let {
            if(ev.action == MotionEvent.ACTION_DOWN &&
                    editTextNumberDecimal.visibility == View.VISIBLE &&
                    editTextNumberDecimal.hasFocus()){
                val rect = Rect()
                editTextNumberDecimal.getGlobalVisibleRect(rect)
                if(!rect.contains(ev.rawX.toInt(), ev.rawY.toInt())){
                    editTextNumberDecimal.clearFocus()
                }
            }
        }
        return super.dispatchTouchEvent(ev)
    }
}