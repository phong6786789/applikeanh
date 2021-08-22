package com.subi.likeanh.model

data class NapRut(
    var uid: String,
    var money: String,
    var packages: String,
    var time: String,
    var isRut: Boolean,
    var status: Boolean,
) {
    constructor() : this(
        "",
        "0",
        "0", "0", false, false
    )
}