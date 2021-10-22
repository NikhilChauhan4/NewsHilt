package com.example.newshilt.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.data.Source

import com.example.newshilt.R
import com.example.newshilt.databinding.SourceItemBinding
import javax.inject.Inject

class SourceAdapter @Inject constructor() :
    PagingDataAdapter<Source, SourceAdapter.ViewHolder>(SourceComparator) {
    var onItemClick: ((String) -> Unit)? = null

    inner class ViewHolder constructor(private val binding: SourceItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Source) = with(binding) {
            source = item
        }

        init {
            itemView.setOnClickListener {
                val url = binding.source?.url ?: ""
                onItemClick?.invoke(url)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: SourceItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.source_item,
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

}

object SourceComparator : DiffUtil.ItemCallback<Source>() {
    override fun areItemsTheSame(oldItem: Source, newItem: Source) =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Source, newItem: Source) =
        oldItem == newItem
}