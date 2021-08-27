package com.subi.likeanh.utils

import android.annotation.SuppressLint
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.subi.likeanh.R
import com.subi.likeanh.model.History
import com.subi.likeanh.model.NapRut
import java.lang.Exception
import java.text.DecimalFormat

object BindingUtils {
    @BindingAdapter("setImageResource")
    @JvmStatic
    fun setImageResource(imageView: ImageView, src: Int?) {
        if (src != null) {
            imageView.setBackgroundResource(src)
        }
    }

    @BindingAdapter("convertTextForInCome")
    @JvmStatic
    fun convertIntToString(textView: TextView, value: String) {
        textView.text = "Bạn đã được cộng + $value"
    }


    @BindingAdapter("setTextForUserName")
    @JvmStatic
    fun convertIntToStringA(textView: TextView, value: String) {
        textView.text = "Tên tài khoản $value"
    }


    @BindingAdapter("setTextForUserMoney")
    @JvmStatic
    fun convertIntToStringA1(textView: TextView, value: String) {
        val fm = DecimalFormat("#,###")
        textView.text = "Bạn đã nạp ${fm.format(value.toLong())} VNĐ"
    }

    @BindingAdapter("setImageForIncomeType")
    @JvmStatic
    fun incomeType(img: ImageView, history: NapRut) {
        if (!history.isRut) {
            Glide.with(img).load(R.drawable.ic_plus).into(img)
            return
        }
        Glide.with(img).load(R.drawable.ic_minus).into(img)
    }


    @SuppressLint("ResourceAsColor")
    @BindingAdapter("setTextForIncomeType")
    @JvmStatic
    fun incomeType(tv: TextView, history: NapRut) {
        val fm = DecimalFormat("#,###")
        val money = history.money.replace(",", "").replace(" đồng", "").trim()
        if (!history.isRut) {
            tv.text = "Bạn đã nạp + $money VNĐ"
            tv.setTextColor(R.color.nap)
            return
        }
        tv.text = "Bạn đã rút - $money VNĐ"
        tv.setTextColor(R.color.rut)

    }


    @BindingAdapter("isLove")
    @JvmStatic
    fun isLove(imageView: ImageView, isLove: Boolean?) {
        if (isLove == true) {
            imageView.setImageResource(R.drawable.like)
        } else {
            imageView.setImageResource(R.drawable.like_not)
        }
    }

    @BindingAdapter("setMoney")
    @JvmStatic
    fun setMoney(textView: TextView, money: String?) {
        Log.d("test", money.toString())
        try {
            if (money == "0" || money == "null") {
                textView.text = "Số tiền hiện tại của bạn là: 0 VNĐ"
            } else {
                val fm = DecimalFormat("#,###")
                textView.text = "Số tiền hiện tại của bạn là ${fm.format(money?.toLong())} VNĐ"
            }
        } catch (e: Exception) {

        }
    }


    @BindingAdapter("setGoi")
    @JvmStatic
    fun setGoi(textView: TextView, goi: String?) {
        try {
            if (goi == "0" || goi == "null") {
                textView.text = "Gói của bạn đang dùng là 0"
            } else {
                textView.text = "Gói của bạn đang dùng là $goi"
            }
        } catch (e: Exception) {
        }
    }

    @BindingAdapter("setGT")
    @JvmStatic
    fun setGT(textView: TextView, gt: String?) {
        try {
            if (gt == "0" || gt == "null") {
                textView.text = "Đã giới thiệu: 0"
            } else {
                textView.text = "Đã giới thiệu: $gt"
            }
        } catch (e: Exception) {
        }
    }
}