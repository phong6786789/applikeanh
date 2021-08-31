package com.subi.likeanh.money

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.UiThread
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.subi.likeanh.BR
import com.subi.likeanh.R
import com.subi.likeanh.databinding.FragmentMoneyBinding
import com.subi.likeanh.model.Admin


class MoneyFragment : Fragment() {
    private lateinit var binding: FragmentMoneyBinding
    private val viewModel: MoneyViewModel by viewModels()
    private val adminDatabase = FirebaseDatabase.getInstance().getReference("admin")
    private val imageDatabase = FirebaseDatabase.getInstance().getReference("image")
    private val mStorageRef = FirebaseStorage.getInstance().getReference("image/background.png");

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
                findNavController().navigate(R.id.action_moneyFragment_to_rut2Fragment)
            }

            layoutThunhap.setOnClickListener {
                findNavController().navigate(R.id.action_moneyFragment_to_thuNhapFragment)
            }

            layoutLichsu.setOnClickListener {
                findNavController().navigate(R.id.action_moneyFragment_to_lichSuFragment)
            }

            layoutMagt.setOnClickListener {

            }
            imgContact.setOnClickListener {
                makeThePhoneCallToTheAdmin()
            }
        }
        setImageForImageView()

        return binding.root;
    }
    @UiThread
    private fun setImageForImageView() {
        try {
            mStorageRef.downloadUrl.addOnSuccessListener {
                Glide.with(requireActivity()).load(it).into(binding.imgCenter)
            }
        } catch (e: Exception) {
        }
    }

    private fun makeThePhoneCallToTheAdmin() {
        adminDatabase.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val admin = snapshot.getValue(Admin::class.java)
                val phone = admin?.sdt
                val intent = Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null))
                startActivity(intent)
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }


    fun init() {
        binding.setVariable(BR.viewModel, viewModel)
        requireActivity().findViewById<BottomNavigationView>(R.id.bottom_nav).visibility =
            View.VISIBLE
        viewModel.load()
    }
}