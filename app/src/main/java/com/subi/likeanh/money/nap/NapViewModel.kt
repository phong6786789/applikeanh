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
        Money("1", "300,000 VNĐ", "30 like/ngày", "400 VNĐ/like", "3 ngày","36,000 VNĐ", "Đã giới thiệu 3 người", "336,000 VNĐ"),
        Money("2", "3,000,000 VNĐ", "30 like/ngày", "19,280 VNĐ/like", "7 ngày",  "945,000 VNĐ", "Đã giới thiệu 3 người", "3,945,000 VNĐ"),
        Money("3", "10,000,000 VNĐ", "30 like/ngày", "25,000 VNĐ/like", "20 ngày", "10,000,000 VNĐ", "Đã giới thiệu 3 người", "20,000,000 VNĐ"),
        Money("4", "30,000,000 VNĐ", "35 like/ngày", "55,000 VNĐ/like", "30 ngày", "49,500,000 VNĐ", "Đã giới thiệu 3 người", "79,500,000 VNĐ"),
        Money("5", "50,000,000 VNĐ", "35 like/ngày", "75,000 VNĐ/like", "40 ngày","120,000,000 VNĐ", "Đã giới thiệu 3 người", "170,000,000 VNĐ"),
        Money("6", "100,000,000 VNĐ", "40 like/ngày", "162,500 VNĐ/like", "50 ngày","325,000,000 VNĐ", "Đã giới thiệu 3 người", "425,000,000 VNĐ"),
        Money("7", "200,000,000 VNĐ", "40 like/ngày", "205,882 VNĐ/like", "68 ngày", "952,000,000 VNĐ", "", "1,152,000,000 VNĐ")
    )
    var money: ObservableField<Money> = ObservableField()

    fun load(){

    }
}