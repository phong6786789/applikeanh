package com.subi.likeanh.utils

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import com.subi.likeanh.databinding.ProgressbarBinding


@SuppressLint("StaticFieldLeak")
object LoadingDialog {
    var context: Context? = null
    var dialog: Dialog? = null
    fun getInstance(context: Context):LoadingDialog {
        this.context = context
        return LoadingDialog
    }

    fun show() {
        context?.apply {
            dialog = Dialog(this)
            val binding = ProgressbarBinding.inflate(LayoutInflater.from(context))
            dialog?.apply {
                setContentView(binding.root)
                setCancelable(true)
                window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT)
                if (window != null) {
                    window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                }
                show()
            }
        }
    }

    fun dismiss() {
        dialog?.dismiss()
    }

}