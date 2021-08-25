package com.subi.likeanh.utils

import com.google.firebase.auth.FirebaseAuth
import java.text.DecimalFormat

object Utils {
   fun getUID(): String {
        return FirebaseAuth.getInstance().currentUser?.uid?:""
    }

    fun getFMoney(string:String):String{
        return DecimalFormat("#,###").format(string.toLong())
    }
}