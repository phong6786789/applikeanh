package com.subi.likeanh.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.subi.likeanh.BR
import com.subi.likeanh.databinding.ItemLichSuGiaoDichBinding
import com.subi.likeanh.databinding.ItemRankBinding
import com.subi.likeanh.databinding.ItemThuNhapBinding

import com.subi.likeanh.model.History
import com.subi.likeanh.model.Income
import com.subi.likeanh.model.User


class RankAdapter(
    var items: List<User>
) : RecyclerView.Adapter<RankAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        ItemRankBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )


    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binData(items[position], position)
    }

    inner class ViewHolder(var binding: ItemRankBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun binData(user: User, position: Int) {
            binding.apply {
                setVariable(BR.user, user)
                executePendingBindings()
            }
        }
    }

    fun setNewData(newItems: List<User>) {
        this.items = newItems
        notifyDataSetChanged()
    }


}