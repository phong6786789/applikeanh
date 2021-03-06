package com.subi.likeanh.model

data class Money
    (
    var goi: String,
    var gia: String,
    var like: String,
    var gia_like: String,
    var tgian: String,
    var hoahong: String,
    var dieukien: String,
    var tong: String,
)

fun getListMoney(): ArrayList<Money> {
    return arrayListOf(
        Money(
            "1",
            "300,000 đồng",
            "30 like/ngày",
            "400",
            "3 ngày",
            "36,000 đồng",
            "Đã giới thiệu 10 người",
            "336,000 đồng"
        ),
        Money(
            "2",
            "3,000,000 đồng",
            "30 like/ngày",
            "19280",
            "7 ngày",
            "945,000 đồng",
            "Đã giới thiệu 20 người",
            "3,945,000 đồng"
        ),
        Money(
            "3",
            "10,000,000 đồng",
            "30 like/ngày",
            "25000",
            "20 ngày",
            "10,000,000 đồng",
            "Đã giới thiệu 30 người",
            "20,000,000 đồng"
        ),
        Money(
            "4",
            "30,000,000 đồng",
            "35 like/ngày",
            "55000",
            "30 ngày",
            "49,500,000 đồng",
            "Đã giới thiệu 40 người",
            "79,500,000 đồng"
        ),
        Money(
            "5",
            "50,000,000 đồng",
            "35 like/ngày",
            "75000",
            "40 ngày",
            "120,000,000 đồng",
            "Đã giới thiệu 50 người",
            "170,000,000 đồng"
        ),
        Money(
            "6",
            "100,000,000 đồng",
            "40 like/ngày",
            "162500",
            "50 ngày",
            "325,000,000 đồng",
            "Đã giới thiệu 60 người",
            "425,000,000 đồng"
        ),
        Money(
            "7",
            "200,000,000 đồng",
            "40 like/ngày",
            "205882",
            "68 ngày",
            "952,000,000 đồng",
            "",
            "1,152,000,000 đồng"
        )
    )
}