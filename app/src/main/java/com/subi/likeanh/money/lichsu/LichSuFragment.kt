package com.subi.likeanh.money.lichsu

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import com.subi.likeanh.adapter.LichSuAdapter
import com.subi.likeanh.databinding.FragmentLichSuBinding
import com.subi.likeanh.model.Income

class LichSuFragment : Fragment() {
    private var lichSuAdapter: LichSuAdapter? = null
    private lateinit var binding: FragmentLichSuBinding
    private val viewModel: LichSuModel by viewModels()
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
        binding = FragmentLichSuBinding.inflate(inflater, container, false)
        init()
        initRecyclerViews()
        checkForSetDataToUserFragment()
        return binding.root;
    }

    private fun initRecyclerViews() {
        val list = arrayListOf<Income>()
        lichSuAdapter = LichSuAdapter(list)
        binding.apply {
            rcvLichSu.apply {
                adapter = lichSuAdapter
                layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                hasFixedSize()
            }
        }
        checkForSetDataToUserFragment()
    }

    fun init() {
        binding.setVariable(BR.viewModel, viewModel)
        requireActivity().findViewById<BottomNavigationView>(R.id.bottom_nav).visibility =
            View.VISIBLE
    }

    private fun checkForSetDataToUserFragment() {

        if (user != null) {
            incomeDatabase.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val list = arrayListOf<Income>()
                    for (data in snapshot.children) {
                        val income = data.getValue(Income::class.java)
                        list.add(income!!)
                    }
                    lichSuAdapter?.setNewData(list)
                    Log.d(TAG, "onDataChange: ${list.size}")

                }

                override fun onCancelled(error: DatabaseError) {
                }

            })
        }
    }


    companion object {
        private const val TAG = "LichSuFragment"
    }
}