package com.subi.likeanh.utils

import android.util.Log
import com.ajts.androidmads.telegrambotlibrary.Telegram
import com.ajts.androidmads.telegrambotlibrary.Utils.TelegramCallback
import com.ajts.androidmads.telegrambotlibrary.models.Message
import okhttp3.Call
import android.widget.Toast

import com.subi.likeanh.MainActivity





object SendTelegram {
    fun send(text: String) {
        val token = "1961017625:AAGSSLzpvxW_eaQUqo686YSEZ0CVaqpwlW4"
        val idChat = "#1961017625"
        val telegram = Telegram(token)
        telegram.sendMessage(idChat, text,
            object : TelegramCallback<Message?> {
                override fun onFailure(call: Call?, e: Exception?) {}
                override fun onResponse(call: Call?, response: Message?) {
                    Log.v("response.body()", response?.isOk.toString())
                }
            })
    }
}