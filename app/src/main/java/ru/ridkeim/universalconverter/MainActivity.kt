package ru.ridkeim.universalconverter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.ridkeim.universalconverter.R
import com.ridkeim.universalconverter.databinding.ActivityMainBinding

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
            }
        }
        mainViewModel.converters.observe(this) {
            it?.let {
                universalConverterAdapter.submitList(it)
            }
        }
        binding.lifecycleOwner = this
    }
}