package com.mackhartley.temptracker.ui.fevers

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mackhartley.temptracker.R
import com.mackhartley.temptracker.data.models.Fever
import com.mackhartley.temptracker.databinding.ItemFeverBinding
import com.mackhartley.temptracker.utils.toStandardFormat

class FeversListAdapter(
    private val clickListener: FeverItemClickListener
) : ListAdapter<Fever, FeversListAdapter.FeverVH>(FeverItemDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeverVH {
        val binding = ItemFeverBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FeverVH(binding, clickListener)
    }

    override fun onBindViewHolder(holder: FeverVH, position: Int) {
        val item = currentList[position]
        holder.bind(item)
    }

    class FeverVH(
        itemBinding: ItemFeverBinding,
        private val clickListener: FeverItemClickListener
    ) : RecyclerView.ViewHolder(itemBinding.root), View.OnClickListener {

        private val feverTitle = itemBinding.feverTitle
        private val feverDate = itemBinding.feverDate
        var feverId: Int? = null

        init { itemView.setOnClickListener(this) }

        fun bind(fever: Fever) {
            val context = feverTitle.context // Seems like a meh way to get context, but actually suggested here: https://stackoverflow.com/questions/32136973/how-to-get-a-context-in-a-recycler-view-adapter
            feverTitle.setText(fever.name)
            val dateLabel = "${context.getString(R.string.created_on)} ${fever.dateTime.toStandardFormat()}"
            feverDate.setText(dateLabel)
            feverId = fever.id
        }

        override fun onClick(p0: View?) {
            val safeFeverId = feverId
            if (safeFeverId != null) {
                clickListener.itemClicked(safeFeverId)
            } else {
                Toast.makeText(feverTitle.context, "Error: feverId is null", Toast.LENGTH_SHORT).show()
            }
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

    interface FeverItemClickListener {
        fun itemClicked(feverId: Int)
    }
}

