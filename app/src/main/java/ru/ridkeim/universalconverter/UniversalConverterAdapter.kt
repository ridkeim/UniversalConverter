package ru.ridkeim.universalconverter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ridkeim.universalconverter.R
import com.ridkeim.universalconverter.databinding.ListConvertersItemBinding

class UniversalConverterAdapter(private val clickListener: UniversalConverterListener) : ListAdapter<UniversalConverter, UniversalConverterAdapter.ViewHolder>(UniversalConverterDifUtilCallback()){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position)!!,clickListener)
    }

    class ViewHolder private constructor(private val binding : ListConvertersItemBinding) : RecyclerView.ViewHolder(binding.root), View.OnClickListener{

        private lateinit var clickListener : UniversalConverterListener

        fun bind(item: UniversalConverter, clickListener: UniversalConverterListener){
            val condition = lastCheckedPosition == adapterPosition
            if(condition){
                lastViewHolder = this
            }
            binding.item = item
            binding.checked = condition
            binding.listConverterItem.setOnClickListener(this)
            this.clickListener = clickListener
            binding.executePendingBindings()
        }

        override fun onClick(v: View?) {
            if(lastCheckedPosition != adapterPosition){
                if(lastViewHolder != this){
                    lastViewHolder?.setCheckedField(false)
                }
                this.setCheckedField(true)
                updateLastPosition()
            }
            binding.item?.let {
                clickListener.onClick(it)
            }
        }

        private fun setCheckedField(value : Boolean){
            binding.checked = value
        }

        private fun updateLastPosition(){
            lastViewHolder = this
            lastCheckedPosition = adapterPosition
        }

        companion object {
            var lastViewHolder : ViewHolder? = null
            var lastCheckedPosition = 0

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
