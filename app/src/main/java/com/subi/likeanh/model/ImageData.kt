package com.subi.likeanh.model

data class ImageData(
    val data : String,
    val link : String,
) {
    constructor() : this(
        "",
        "",
    )
}