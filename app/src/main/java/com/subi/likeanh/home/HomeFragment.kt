package com.subi.likeanh.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.subi.likeanh.BR
import com.subi.likeanh.R
import com.subi.likeanh.adapter.HomeAdapter
import com.subi.likeanh.databinding.FragmentHomeBinding
import com.subi.likeanh.model.Product

class HomeFragment : Fragment() {
    lateinit var binding: FragmentHomeBinding
    private val viewModel: HomeViewModel by viewModels()
    var list  = arrayListOf<Product>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        init()
        list.add(Product("test", R.drawable.gradien3, false))
        list.add(Product("test2", R.drawable.gradien2, false))
        list.add(Product("test3", R.drawable.gradien, true))
        list.add(Product("test4", R.drawable.gradien2, false))
        list.add(Product("test5", R.drawable.gradien3, false))
        binding.apply {
            rcv.apply {
                adapter = HomeAdapter(list)
                layoutManager = GridLayoutManager(requireActivity(), 2)
                hasFixedSize()
            }
        }

        return binding.root;
    }
    fun init(){
        binding.setVariable(BR.viewModel, viewModel)
        requireActivity().findViewById<BottomNavigationView>(R.id.bottom_nav).visibility = View.VISIBLE
    }
}