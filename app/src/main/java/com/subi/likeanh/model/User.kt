package com.subi.likeanh.model

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
    var totalMoney: String

) {
    constructor() : this("", "", "", "", true, "", "", "", "", "", "", "")
}