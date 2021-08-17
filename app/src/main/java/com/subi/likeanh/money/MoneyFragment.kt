package com.subi.likeanh.money

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.subi.likeanh.BR
import com.subi.likeanh.R
import com.subi.likeanh.databinding.FragmentHomeBinding
import com.subi.likeanh.databinding.FragmentMoneyBinding
import com.subi.likeanh.home.HomeViewModel

class MoneyFragment : Fragment() {
    private lateinit var binding: FragmentMoneyBinding
    private val viewModel: MoneyViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentMoneyBinding.inflate(inflater, container, false)
        init()
        binding.apply {
            layoutNap.setOnClickListener {
                findNavController().navigate(R.id.action_moneyFragment_to_napFragment)
            }

            layoutRut.setOnClickListener {

            }

            layoutThunhap.setOnClickListener {

            }

            layoutLichsu.setOnClickListener {

            }

            layoutMagt.setOnClickListener {

            }
        }

        return binding.root;
    }

    fun init() {
        binding.setVariable(BR.viewModel, viewModel)
        requireActivity().findViewById<BottomNavigationView>(R.id.bottom_nav).visibility =
            View.VISIBLE
    }
}