package com.subi.likeanh.model

data class History(
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