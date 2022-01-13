package com.mackhartley.temptracker.ui.feverhistory

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mackhartley.temptracker.data.models.Fever
import com.mackhartley.temptracker.data.models.TempLog
import com.mackhartley.temptracker.databinding.FragmentFeverHistoryBinding
import com.mackhartley.temptracker.databinding.ItemFeverBinding
import com.mackhartley.temptracker.databinding.ItemTempBinding
import com.mackhartley.temptracker.ui.fevers.FeversAdapter
import com.mackhartley.temptracker.utils.getFormattedTime
import com.mackhartley.temptracker.utils.toStandardFormat
import timber.log.Timber

class TempAdapter(
    private val clickListener: TempItemClickListener
) : ListAdapter<TempLog, TempAdapter.TempVH>(TempLogDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TempVH {
        val binding = ItemTempBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TempVH(binding, clickListener)
    }

    override fun onBindViewHolder(holder: TempVH, position: Int) {
        val item = currentList[position]
        holder.bind(item)
    }

    class TempVH(
        itemBinding: ItemTempBinding,
        private val clickListener: TempItemClickListener
    ) : RecyclerView.ViewHolder(itemBinding.root), View.OnClickListener {
        private val tempValue = itemBinding.tempValue
        private val tempDate = itemBinding.tempDate
        private val tempTime = itemBinding.tempTime
        private var tempLog: TempLog? = null

        init {
            itemView.setOnClickListener(this)
        }

        fun bind(tempLog: TempLog) {
            tempValue.setText(tempLog.temp.toString())
            tempDate.setText(tempLog.dateCreated.toStandardFormat())
            tempTime.setText(getFormattedTime(tempLog.dateCreated))
            this.tempLog = tempLog
        }

        override fun onClick(p0: View?) {
            val safeTemp = tempLog
            if (safeTemp != null) {
                clickListener.itemClicked(safeTemp)
            } else {
                Timber.e("TempLog is null when TempVH clicked in TempAdapter")
            }
        }
    }

    private object TempLogDiffCallback : DiffUtil.ItemCallback<TempLog>() {
        override fun areItemsTheSame(oldItem: TempLog, newItem: TempLog): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: TempLog, newItem: TempLog): Boolean {
            return oldItem == newItem
        }
    }

    interface TempItemClickListener {
        fun itemClicked(tempLog: TempLog)
    }
}

