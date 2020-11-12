package ru.ridkeim.universalconverter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckedTextView

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ridkeim.universalconverter.databinding.ListConvertersItemBinding

class UniversalConverterAdapter(val clickListener: UniversalConverterListener) : ListAdapter<UniversalConverter, UniversalConverterAdapter.ViewHolder>(UniversalConverterDifUtilCallback()){

    companion object{
        var lastCheckedItem : UniversalConverter? = null
        var lastCheckedTextView : CheckedTextView? = null
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position)!!,clickListener)
    }

    fun setChecked(item: UniversalConverter) {
        lastCheckedItem = item
    }

    class ViewHolder private constructor(val binding : ListConvertersItemBinding) : RecyclerView.ViewHolder(binding.root), View.OnClickListener{

        private lateinit var clickListener : UniversalConverterListener

        fun bind(item: UniversalConverter, clickListener: UniversalConverterListener){
            binding.item = item
            if(item == lastCheckedItem){
                binding.listConverterItem.isChecked = true
                lastCheckedTextView = binding.listConverterItem
            }
            this.clickListener = clickListener
            binding.listConverterItem.setOnClickListener(this)
            binding.executePendingBindings()
        }

        override fun onClick(v: View?) {
            if(lastCheckedTextView != binding.listConverterItem){
                lastCheckedTextView?.isChecked = false
                lastCheckedTextView = binding.listConverterItem
                lastCheckedTextView?.isChecked = true
            }
            binding.item?.let {
                clickListener.onClick(it)
            }
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListConvertersItemBinding.inflate(layoutInflater,parent,false)
                return ViewHolder(binding)
            }
        }


    }

    class UniversalConverterDifUtilCallback : DiffUtil.ItemCallback<UniversalConverter>(){
        override fun areItemsTheSame(oldItem: UniversalConverter, newItem: UniversalConverter): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: UniversalConverter, newItem: UniversalConverter): Boolean {
            return oldItem == newItem
        }

    }
}

class UniversalConverterListener (private val clickListener : (UniversalConverter) -> Unit){
    fun onClick(item : UniversalConverter) = clickListener(item)
}
