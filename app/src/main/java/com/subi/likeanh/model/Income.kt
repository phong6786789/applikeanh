package com.subi.likeanh.model

data class Income(
    val userName: String,
    val userMoneyUp: String,
    val userDate: String,
    val userType: String
) {
    constructor() : this(
        "",
        "",
        "", ""
    )
}