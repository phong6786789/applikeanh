package com.subi.likeanh.base

import android.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding



abstract class BaseFragment<T : ViewDataBinding?>(private var layoutBinding: T? = null) :
    Fragment() {

    private lateinit var viewRoot: View

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        this.layoutBinding =
            DataBindingUtil.inflate<T>(inflater, this.initLayout(), container, false)
        this.viewRoot = layoutBinding!!.root

        /*
        * 1
        * */
        this.init()

        /*
        * 2
        * */
        this.initViews()
        this.setOnClickForViews()

        return this.viewRoot
    }

    protected abstract fun initLayout(): Int

    protected abstract fun init()

    protected abstract fun setOnClickForViews()

    protected abstract fun initViews()

}