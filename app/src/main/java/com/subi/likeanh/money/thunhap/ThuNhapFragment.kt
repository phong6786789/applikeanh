package com.subi.likeanh.money.thunhap

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.subi.likeanh.BR
import com.subi.likeanh.R

import com.subi.likeanh.databinding.FragmentThuNhapBinding
import com.subi.likeanh.model.Income
import com.subi.likeanh.model.User
import com.subi.likeanh.utils.LoadingDialog


class ThuNhapFragment : Fragment() {
    private lateinit var binding: FragmentThuNhapBinding
    private val viewModel: ThuNhapViewModel by viewModels()
    private var user = FirebaseAuth.getInstance().currentUser
    private val incomeDatabase =
        FirebaseDatabase.getInstance().getReference("income").child(user!!.uid)
    private val userDatabase =
        FirebaseDatabase.getInstance().getReference("user").child(user!!.uid)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentThuNhapBinding.inflate(inflater, container, false)
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