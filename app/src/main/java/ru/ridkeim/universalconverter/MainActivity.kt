package ru.ridkeim.universalconverter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.core.view.inputmethod.EditorInfoCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
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

        val universalConverterAdapter = UniversalConverterAdapter(UniversalConverterListener {
            Toast.makeText(this, it.title, Toast.LENGTH_SHORT).show()
            mainViewModel.onUniversalConverterClicked(it)

        })

        binding.listConverter.adapter = universalConverterAdapter

        mainViewModel.currentConverter.observe(this) {
            it?.let {
                universalConverterAdapter.setChecked(it)
                editTextNumberDecimal.onEditorAction(EditorInfo.IME_ACTION_NEXT)

            }
        }

        mainViewModel.converters.observe(this) {
            it?.let {
                universalConverterAdapter.submitList(it)
            }
        }

        mainViewModel.numberFormatException.observe(this){
             if(true == it){
                 Toast.makeText(this, "Неверно введено число", Toast.LENGTH_SHORT).show()
                 mainViewModel.errorToastShown()
             }
        }

        binding.editTextNumberDecimal.setOnEditorActionListener { v, actionId, event ->
            return@setOnEditorActionListener when (actionId) {
                EditorInfo.IME_ACTION_DONE -> {
                    v.clearFocus()
                    mainViewModel.onTextEditDone(v.text.toString())
                    true
                }
                EditorInfo.IME_ACTION_NEXT -> {
                    mainViewModel.onUpdateTextEdit()
                    true
                }
                else -> false
            }
        }
        binding.lifecycleOwner = this
    }
}