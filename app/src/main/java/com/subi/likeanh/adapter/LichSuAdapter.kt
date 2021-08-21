package com.subi.likeanh.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.subi.likeanh.BR
import com.subi.likeanh.databinding.ItemLichSuGiaoDichBinding

import com.subi.likeanh.model.History


class LichSuAdapter(
    var items: List<History>
) : RecyclerView.Adapter<LichSuAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        ItemLichSuGiaoDichBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )


    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binData(items[position], position)
    }

    inner class ViewHolder(var binding: ItemLichSuGiaoDichBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun binData(history: History, position: Int) {
            binding.apply {
                setVariable(BR.income, history)
                executePendingBindings()
            }
        }
    }

    fun setNewData(newItems: List<History>) {
        this.items = newItems
        notifyDataSetChanged()
    }


}