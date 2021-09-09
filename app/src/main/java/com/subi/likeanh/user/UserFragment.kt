package com.subi.likeanh.user

import android.R.attr
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.database.FirebaseDatabase
import com.subi.likeanh.BR
import com.subi.likeanh.R
import com.subi.likeanh.databinding.FragmentHomeBinding
import com.subi.likeanh.databinding.FragmentUserBinding
import com.subi.likeanh.home.HomeViewModel
import com.google.firebase.auth.FirebaseAuth

import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.subi.likeanh.model.User
import kotlin.math.log
import android.R.attr.label

import android.content.ClipData
import androidx.core.content.ContextCompat

import androidx.core.content.ContextCompat.getSystemService
import android.R.attr.label
import android.content.Context.CLIPBOARD_SERVICE
import androidx.compose.ui.platform.ClipboardManager

import androidx.core.content.ContextCompat.getSystemService
import com.google.api.Context


class UserFragment : Fragment(), View.OnClickListener {
    private lateinit var binding: FragmentUserBinding
    private val viewModel: UserViewModel by viewModels()
    private var user = FirebaseAuth.getInstance().currentUser
    private var auth = FirebaseAuth.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentUserBinding.inflate(inflater, container, false)
        init()
        checkForSetDataToUserFragment()
        setOnClickForViews()
        return binding.root;
    }

    private fun setOnClickForViews() {
        binding.tvMoveToScreenUpdatesUser.setOnClickListener(this)
        binding.tvLogOut.setOnClickListener(this)
        binding.tvUserPhoneNumber.setOnClickListener(this)
    }

    private fun checkForSetDataToUserFragment() {
        if (user != null) {
            val ref =
                FirebaseDatabase.getInstance().getReference("user").child(user!!.uid)
            ref.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val user = snapshot.getValue(User::class.java)
                    viewModel.user.set(user)
                }

                override fun onCancelled(error: DatabaseError) {
                }

            })
        }
    }

    companion object {
        private const val TAG = "Kienda"
    }

    fun init() {
        binding.setVariable(BR.viewModel, viewModel)
        requireActivity().findViewById<BottomNavigationView>(R.id.bottom_nav).visibility =
            View.VISIBLE
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.tvMoveToScreenUpdatesUser -> {
                startToAnotherScreen()
            }
            R.id.tvLogOut -> {
                logOutOfTheUser()
            }
            R.id.tvUserPhoneNumber -> {

            }
        }
    }



    private fun logOutOfTheUser() {
        auth.signOut()
        findNavController().navigate(R.id.loginFragment)
    }

    private fun startToAnotherScreen() {
        findNavController().navigate(R.id.editUserFragment)
    }
}