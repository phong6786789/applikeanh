package com.subi.likeanh.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
    var uid: String,
    var phone: String,
    var name: String,
    var token: String,
    var status: Boolean,
    var bank: String,
    var stk: String,
    var sdtGt: String,
    var idPhone: String,
    var timesIntroduce: String,
    var transferTime: String,
    var totalMoney: String,
    var userPackage: String,
    var numberLikes: String,
    var isAvailableToLike: String,
    val currentDate: String,
    val totalLike: String,
    val index: String,
    val tempDate: String,
    val timeAvailableForUserPackage: String
) : Parcelable {
    constructor() : this(
        "",
        "",
        "",
        "",
        true,
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "true",
        "",
        "",
        "0",
        "", ""
    )
}