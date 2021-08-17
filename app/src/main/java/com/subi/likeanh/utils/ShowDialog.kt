package com.subi.nails2022.view

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.subi.likeanh.databinding.DialogBinding

interface DialogLeftInterface {
    fun onClick()
}

interface DialogRightInterface {
    fun onClick()
}
class ShowDialog( //add private constructor if necessary
    val title: String?,
    val message: String?,
    val leftButton: String?,
    val rightButton: String?)  {
    private constructor(builder: Builder) : this(builder.title, builder.message, builder.leftButton, builder.rightButton)

    class Builder(context: Context) {

        var context: Context? = context
            private set
        var title: String? = null
            private set

        var message: String? = null
            private set

        var leftButton: String? = null

        var rightButton: String? = null

        var leftListener: DialogLeftInterface? = null
            private set

        var rightListener: DialogRightInterface? = null


        fun title(title: String) = apply { this.title = title }

        fun message(mes: String) = apply { this.message = mes }

        fun leftButton(left: String) = apply { this.leftButton = left }

        fun rightButton(right: String) = apply { this.rightButton = right }


        fun setLeftButton(text: String, onClick: DialogLeftInterface) = apply {
            this.leftButton = text
            this.leftListener = onClick
        }

        fun setRightButton(text: String, onClick: DialogRightInterface) = apply {
            this.rightButton = text
            this.rightListener = onClick
        }


        fun confirm(): Dialog? {
            val dialog = context?.let { Dialog(it) }
            val binding = DialogBinding.inflate(LayoutInflater.from(context))
            dialog?.apply {
               setContentView(binding.root)
                window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
                if (window != null) {
                    window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                }
//              window?.setWindowAnimations(R.style.Animation_Activity)
                binding.title = title
                binding.des = message
                binding.btnLeft.text = leftButton
                binding.btnRight.text = rightButton
                binding.btnLeft.setOnClickListener{
                    leftListener?.onClick()
                }
                binding.btnRight.setOnClickListener{
                    rightListener?.onClick()
                }

            }
            return dialog
        }
        fun show( tit:String, mess:String){
            val dialog = context?.let { Dialog(it) }
            val binding = DialogBinding.inflate(LayoutInflater.from(context))
            dialog?.apply {
                setContentView(binding.root)
                window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
                if (window != null) {
                    window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                }
//              window?.setWindowAnimations(R.style.Animation_Activity)
                binding.title = tit
                binding.des = mess
                binding.btnLeft.visibility = View.GONE
                binding.btnRight.text = "ĐÓNG"
                binding.btnRight.setOnClickListener{
                    dialog.dismiss()
                }
            }
            dialog?.show()
        }
    }


}