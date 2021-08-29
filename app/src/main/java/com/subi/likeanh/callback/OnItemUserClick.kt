package com.subi.likeanh.callback

import com.subi.likeanh.model.User


interface OnItemUserClick {
    fun onShortClick(position: Int, user: User)
}