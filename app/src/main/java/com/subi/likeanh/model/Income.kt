package com.subi.likeanh.model

data class Income(
    var userName: String,
    var userMoney: String,
) {
    constructor() : this(
        "",
        "",
    )
}