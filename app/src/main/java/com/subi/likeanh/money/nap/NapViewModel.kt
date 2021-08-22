package com.subi.likeanh.money.nap

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.subi.likeanh.model.Money
import com.subi.likeanh.model.User

class NapViewModel : ViewModel() {
    var stk = ObservableField("")
    var user: ObservableField<User> = ObservableField()

    val listLoai = arrayListOf(
        "Gói 1", "Gói 2", "Gói 3", "Gói 4", "Gói 5", "Gói 6", "Gói 7"
    )

    var name = ObservableField("")
        var list = arrayListOf(
        Money("1", "300,000 đồng", "30 like/ngày", "400 đồng/like", "3 ngày","36,000 đồng", "Đã giới thiệu 10 người", "336,000 đồng"),
        Money("2", "3,000,000 đồng", "30 like/ngày", "19,280 đồng/like", "7 ngày",  "945,000 đồng", "Đã giới thiệu 20 người", "3,945,000 đồng"),
        Money("3", "10,000,000 đồng", "30 like/ngày", "25,000 đồng/like", "20 ngày", "10,000,000 đồng", "Đã giới thiệu 30 người", "20,000,000 đồng"),
        Money("4", "30,000,000 đồng", "35 like/ngày", "55,000 đồng/like", "30 ngày", "49,500,000 đồng", "Đã giới thiệu 40 người", "79,500,000 đồng"),
        Money("5", "50,000,000 đồng", "35 like/ngày", "75,000 đồng/like", "40 ngày","120,000,000 đồng", "Đã giới thiệu 50 người", "170,000,000 đồng"),
        Money("6", "100,000,000 đồng", "40 like/ngày", "162,500 đồng/like", "50 ngày","325,000,000 đồng", "Đã giới thiệu 60 người", "425,000,000 đồng"),
        Money("7", "200,000,000 đồng", "40 like/ngày", "205,882 đồng/like", "68 ngày", "952,000,000 đồng", "", "1,152,000,000 đồng")
    )
    var money: ObservableField<Money> = ObservableField()

    fun load(){

    }
}