package com.subi.likeanh.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.subi.likeanh.BR
import com.subi.likeanh.callback.OnItemClick
import com.subi.likeanh.databinding.LayoutProductBinding
import com.subi.likeanh.model.Product


class HomeAdapter(
    var items: List<Product>, private val onItemClick: OnItemClick
) : RecyclerView.Adapter<HomeAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        LayoutProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )


    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binData(items[position], position)
    }

    inner class ViewHolder(var binding: LayoutProductBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun binData(product: Product, position: Int) {
            binding.apply {
                setVariable(BR.product, product)
                executePendingBindings()

                if (!product.isLove) {
                    binding.ivLove.setOnClickListener {
                        onItemClick.onItemLoveClick(product, position)
                        product.isLove = true
                        setVariable(BR.product, product)
                        executePendingBindings()
                    }
                }
            }
        }
    }

    fun setNewData(newItems: List<Product>) {
        this.items = newItems
        notifyDataSetChanged()
    }


    interface OnItemClickListener {
        fun onClickItem(value: Product, i: Int)
    }


}