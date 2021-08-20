package com.subi.likeanh.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.subi.likeanh.BR
import com.subi.likeanh.databinding.ItemLichSuGiaoDichBinding
import com.subi.likeanh.databinding.ItemThuNhapBinding

import com.subi.likeanh.model.History
import com.subi.likeanh.model.Income


class ThuNhapAdapter(
    var items: List<Income>
) : RecyclerView.Adapter<ThuNhapAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        ItemThuNhapBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )


    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binData(items[position], position)
    }

    inner class ViewHolder(var binding: ItemThuNhapBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun binData(income: Income, position: Int) {
            binding.apply {
                setVariable(BR.income, income)
                executePendingBindings()
            }
        }
    }

    fun setNewData(newItems: List<Income>) {
        this.items = newItems
        notifyDataSetChanged()
    }


}