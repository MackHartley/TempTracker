package com.mackhartley.temptracker.ui.fevers

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mackhartley.temptracker.R
import com.mackhartley.temptracker.data.models.Fever
import com.mackhartley.temptracker.databinding.ItemFeverBinding
import com.mackhartley.temptracker.utils.toStandardFormat

class FeversListAdapter : ListAdapter<Fever, FeversListAdapter.FeverVH>(FeverItemDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeverVH {
        val binding = ItemFeverBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FeverVH(binding)
    }

    override fun onBindViewHolder(holder: FeverVH, position: Int) {
        val item = currentList[position]
        holder.bind(item)
    }

    class FeverVH(
        itemBinding: ItemFeverBinding
    ) : RecyclerView.ViewHolder(itemBinding.root), View.OnClickListener {

        private val feverTitle = itemBinding.feverTitle
        private val feverDate = itemBinding.feverDate

        init { itemView.setOnClickListener { this } }

        fun bind(fever: Fever) {
            feverTitle.setText(fever.name)
            feverDate.setText(fever.dateTime.toStandardFormat())
        }

        override fun onClick(p0: View?) {
            TODO("Not yet implemented")
        }
    }

    private object FeverItemDiffCallback : DiffUtil.ItemCallback<Fever>() {
        override fun areItemsTheSame(oldItem: Fever, newItem: Fever): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Fever, newItem: Fever): Boolean {
            return oldItem == newItem
        }
    }
}

