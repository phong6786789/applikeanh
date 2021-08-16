package com.subi.likeanh.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.subi.likeanh.R

object BindingUtils {
    @BindingAdapter("setImageResource")
    @JvmStatic
    fun setImageResource(imageView: ImageView, src:Int?){
        if (src != null) {
            imageView.setBackgroundResource(src)
        }
    }

    @BindingAdapter("isLove")
    @JvmStatic
    fun isLove(imageView: ImageView, isLove:Boolean?){
        if (isLove == true){
            imageView.setImageResource(R.drawable.like)
        }
        else{
            imageView.setImageResource(R.drawable.like_not)
        }
    }
}