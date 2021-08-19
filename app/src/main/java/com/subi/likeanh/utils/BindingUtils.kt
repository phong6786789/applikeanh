package com.subi.likeanh.utils

import android.util.Log
import android.widget.AutoCompleteTextView
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.subi.likeanh.R
import org.w3c.dom.Text
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

    @BindingAdapter("setIntToString")
    @JvmStatic
    fun convertIntToString(textView: TextView, number: Int) {
        textView.text = number.toString()
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
            if(money=="0"||money=="null"){
                textView.text = "0 VNĐ"
            }
            else{
                val fm = DecimalFormat("#,###")
                textView.text = "${fm.format(money?.toLong())} VNĐ"
            }
        }
        catch (e:Exception){

        }
    }
}