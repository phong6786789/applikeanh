package com.subi.likeanh.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.subi.likeanh.BR
import com.subi.likeanh.databinding.ItemLichSuGiaoDichBinding

import com.subi.likeanh.model.NapRut


class LichSuAdapter(
    var items: List<NapRut>
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
        fun binData(NapRut: NapRut, position: Int) {
            binding.apply {
                setVariable(BR.income, NapRut)
                executePendingBindings()
            }
        }
    }

    fun setNewData(newItems: List<NapRut>) {
        this.items = newItems
        notifyDataSetChanged()
    }


}