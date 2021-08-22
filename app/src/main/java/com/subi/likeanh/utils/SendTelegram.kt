package com.subi.likeanh.utils

import android.util.Log
import com.ajts.androidmads.telegrambotlibrary.Telegram
import com.ajts.androidmads.telegrambotlibrary.Utils.TelegramCallback
import com.ajts.androidmads.telegrambotlibrary.models.Message
import kotlinx.coroutines.*
import okhttp3.Call
import java.sql.Ref


object SendTelegram {
    suspend fun send(text: String): Boolean {
        var result = false
        val token = "1988057700:AAGMqjH7LPkvA_-PgRZKzVBD1W26MCBzu0E"
        val idChat = "1341951305"
        val telegram = Telegram(token)
        val waitFor = CoroutineScope(Dispatchers.IO).async {
            telegram.sendMessage(idChat, text,
                object : TelegramCallback<Message?> {
                    override fun onFailure(call: Call?, e: Exception?) {}
                    override fun onResponse(call: Call?, response: Message?) {
                        Log.v("response sendMessage: ", response?.isOk.toString())
                        result = response?.isOk == true
                    }
                })
            return@async result
        }
        waitFor.await()
        Log.d("CoroutineScope", "CoroutineScope: $result")
        return result
    }
}