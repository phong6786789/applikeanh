package com.subi.likeanh.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Admin(
    val username: String,
    var pass: String,
    var bank: String,
    var stk: String,
    var name: String,
    var sdt: String,
) : Parcelable {
    constructor() : this(
        "", "", "", "", "", ""
    )
}