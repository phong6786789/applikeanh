package com.subi.likeanh.money.rut

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.subi.likeanh.BR
import com.subi.likeanh.R
import com.subi.likeanh.databinding.FragmentHomeBinding
import com.subi.likeanh.databinding.FragmentMoneyBinding
import com.subi.likeanh.home.HomeViewModel
import com.subi.likeanh.money.MoneyViewModel

class RutFragment : Fragment() {
    lateinit var binding: FragmentMoneyBinding
    private val viewModel: RutViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentMoneyBinding.inflate(inflater, container, false)
        init()
        binding.apply {

        }

        return binding.root;
    }
    fun init(){
        binding.setVariable(BR.viewModel, viewModel)
        requireActivity().findViewById<BottomNavigationView>(R.id.bottom_nav).visibility = View.VISIBLE
    }
}