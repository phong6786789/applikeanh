package com.subi.likeanh.money.ruta

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.subi.likeanh.BR
import com.subi.likeanh.R
import com.subi.likeanh.databinding.FragmentMoneyBinding
import com.subi.likeanh.databinding.FragmentRut2Binding
import com.subi.likeanh.money.rut.RutViewModel

class Rut2Fragment : Fragment() {
    lateinit var binding: FragmentRut2Binding
    private val viewModel: Rut2ViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentRut2Binding.inflate(inflater, container, false)
        init()
        binding.apply {

        }

        return binding.root;
    }

    fun init() {
        binding.setVariable(BR.viewModel, viewModel)
        requireActivity().findViewById<BottomNavigationView>(R.id.bottom_nav).visibility =
            View.VISIBLE
    }
}