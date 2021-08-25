package com.subi.likeanh.money.thunhap

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.subi.likeanh.BR
import com.subi.likeanh.R
import com.subi.likeanh.adapter.ThuNhapAdapter
import com.subi.likeanh.databinding.FragmentThuNhapBinding
import com.subi.likeanh.model.Income


class ThuNhapFragment : Fragment() {
    private lateinit var binding: FragmentThuNhapBinding
    private val viewModel: ThuNhapViewModel by viewModels()
    private var user = FirebaseAuth.getInstance().currentUser

    private val userDatabase =
        FirebaseDatabase.getInstance().getReference("user").child(user!!.uid)
    private val likeDatabaseError =
        FirebaseDatabase.getInstance().getReference("lịke").child(user!!.uid)

    private var thuNhapAdapter: ThuNhapAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentThuNhapBinding.inflate(inflater, container, false)
        init()
        initRecyclerViews()
        return binding.root;
    }

    private fun initRecyclerViews() {
        val list = arrayListOf<Income>()
        thuNhapAdapter = ThuNhapAdapter(list)
        binding.apply {
            rcvThuNhap.apply {
                adapter = thuNhapAdapter
                layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                hasFixedSize()
            }
        }
        checkForSetDataToUserFragment()
    }

    private fun checkForSetDataToUserFragment() {
        if (user != null) {
            likeDatabaseError.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val list = arrayListOf<Income>()
                    for (data in snapshot.children) {
                        val income = data.getValue(Income::class.java)
                        list.add(income!!)
                    }
                    thuNhapAdapter?.setNewData(list)
                    binding.rcvThuNhap.scheduleLayoutAnimation()
                    Log.d(TAG, "onDataChange: ${list.size}")

                }

                override fun onCancelled(error: DatabaseError) {

                }

            })
        }
    }

    companion object {
        private const val TAG = "ThuNhapFragment"
    }


    fun init() {
        binding.setVariable(BR.viewModel, viewModel)
        requireActivity().findViewById<BottomNavigationView>(R.id.bottom_nav).visibility =
            View.VISIBLE
    }
}