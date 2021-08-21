package com.subi.likeanh.utils

import android.annotation.SuppressLint
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.subi.likeanh.R
import com.subi.likeanh.model.History
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
    fun incomeType(img: ImageView, history: History) {
        if (history.userType == "Nap") {
            Glide.with(img).load(R.drawable.ic_plus).into(img)
            return
        }
        Glide.with(img).load(R.drawable.ic_minus).into(img)
    }


    @SuppressLint("ResourceAsColor")
    @BindingAdapter("setTextForIncomeType")
    @JvmStatic
    fun incomeType(tv: TextView, history: History) {
        val fm = DecimalFormat("#,###")
        if (history.userType == "Nap") {
            tv.text = "Bạn đã nạp + ${fm.format(history?.userMoney.toLong())} VNĐ"
            tv.setTextColor(R.color.nap)
            return
        }
        tv.text = "Bạn đã rút - ${fm.format(history?.userMoney.toLong())} VNĐ"
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
                textView.text = "0 VNĐ"
            } else {
                val fm = DecimalFormat("#,###")
                textView.text = "${fm.format(money?.toLong())} VNĐ"
            }
        } catch (e: Exception) {

        }
    }
}