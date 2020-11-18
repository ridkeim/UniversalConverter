package ru.ridkeim.universalconverter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.ridkeim.universalconverter.R
import kotlinx.android.synthetic.main.list_converters_item.view.*

class UniversalConverterAdapter() : BaseAdapter() {

    private var data: List<UniversalConverter> = listOf()

    override fun getCount(): Int {
        return data.size
    }

    fun submitList(list : List<UniversalConverter>){
        data = list
        notifyDataSetChanged()
    }

    override fun getItem(position: Int): UniversalConverter {
        return data[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?) : View {
        val item = getItem(position)
        val convertedView = if(convertView == null){
            val layoutInflater = LayoutInflater.from(parent?.context)
            layoutInflater.inflate(R.layout.list_converters_item, parent, false)
        } else {
            convertView
        }
        convertedView.listConverterItem.text = item.title
        return convertedView
    }
}