package com.subi.likeanh.model

data class Income(
    var userName: String,
    var userMoney: String,
    var userDate: String,
    var userType: String
) {
    constructor() : this(
        "",
        "",
        "", ""
    )
}