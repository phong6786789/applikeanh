package com.subi.likeanh.callback

import com.subi.likeanh.model.Product

interface OnItemClick {
    fun onItemLoveClick(value : Product, position: Int)
}