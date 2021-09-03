package com.subi.likeanh.rank

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.subi.likeanh.R
import com.subi.likeanh.adapter.RankXAdapter
import com.subi.likeanh.databinding.FragmentRankBinding
import com.subi.likeanh.databinding.FragmentRankxBinding
import com.subi.likeanh.model.User
import com.subi.likeanh.utils.LoadingDialog
import com.subi.nails2022.view.ShowDialog

class RankXFragment : Fragment() {
    private lateinit var binding: FragmentRankxBinding
    private val viewModel: RankViewModel by viewModels()
    private var user = FirebaseAuth.getInstance().currentUser
    private lateinit var dialog: ShowDialog.Builder
    private lateinit var loading: LoadingDialog
    private val userDatabase =
        FirebaseDatabase.getInstance().getReference("user")
    private val phoneDatabase =
        FirebaseDatabase.getInstance().getReference("sdtGT")
    private var listCap1 = arrayListOf<User>()
    private var listCap2 = arrayListOf<User>()
    private var listCap3 = arrayListOf<User>()
    val TAG = "phongsubi"
    private var rankAdapter:RankXAdapter?=null
    private var rankAdapter2:RankXAdapter?=null
    private var rankAdapter3:RankXAdapter?=null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentRankxBinding.inflate(inflater, container, false)
        showCap1()
        init()
        getUser()
        return binding.root;
    }

    private fun init(){
        binding.apply {
            tvCap1.setOnClickListener {
                showCap1()
            }

            tvCap2.setOnClickListener {
                showCap2()
            }

            tvCap3.setOnClickListener {
                showCap3()
            }
        }
    }

    private fun getUser() {
        if (user != null) {
            val ref =
                FirebaseDatabase.getInstance().getReference("user").child(user!!.uid)
            ref.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val user = snapshot.getValue(User::class.java)
                    //Get List
                    if (user != null) {
                        loadAllData(user)
                        Log.d(TAG, "getUser: $user")
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                }

            })
        }
    }

    private fun loadAllData(user: User) {
//Get all list from phone number
        val phone = user.phone
        phoneDatabase.child(phone).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (phone in snapshot.children) {
                    Log.d(TAG, "phone: ${phone.value}")
                    //Get user from phone
                    userDatabase.orderByChild("phone").equalTo(phone.value.toString())
                        .addListenerForSingleValueEvent(object : ValueEventListener {
                            override fun onDataChange(snapshot: DataSnapshot) {
                                listCap1.clear()
                                snapshot.children.forEach{
                                    val user = it.getValue(User::class.java)
                                    //Get List
                                        if (user != null) {
                                            listCap1.add(user)
                                        }
                                }

                                binding.rcvRankCap1.apply {
                                    rankAdapter = RankXAdapter(listCap1)
                                    adapter = rankAdapter
                                    layoutManager =
                                        LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                                    hasFixedSize()
                                }
                                Log.d(TAG, "allList 1: ${listCap1.size}")
                                for (x in listCap1){
                                    val phone = x.phone
                                    Log.d(TAG, "phone 2: $phone")
                                    //Get user from phone
                                    userDatabase.orderByChild("phone").equalTo(phone)
                                        .addListenerForSingleValueEvent(object : ValueEventListener {
                                            @SuppressLint("NotifyDataSetChanged")
                                            override fun onDataChange(snapshot: DataSnapshot) {
                                                snapshot.children.forEach{
                                                    val user = it.getValue(User::class.java)
                                                    //Get List
                                                    if (user != null) {
                                                        listCap2.add(user)
                                                    }
                                                }
                                                Log.d(TAG, "user list 2: ${listCap2.size}")
                                                binding.rcvRankCap2.apply {
                                                    adapter = RankXAdapter(listCap2)
                                                    layoutManager =
                                                        LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                                                    hasFixedSize()
                                                }
                                                Log.d(TAG, "allList 2: ${listCap2.size}")


                                                for (x in listCap2){
                                                    val phone = x.phone
                                                    //Get user from phone
                                                    userDatabase.orderByChild("phone").equalTo(phone)
                                                        .addListenerForSingleValueEvent(object : ValueEventListener {
                                                            override fun onDataChange(snapshot: DataSnapshot) {
                                                                snapshot.children.forEach{
                                                                    val user = it.getValue(User::class.java)
                                                                    //Get List
                                                                    if (user != null) {
                                                                        listCap3.add(user)
                                                                        Log.d(TAG, "user list 3: $user")
                                                                    }
                                                                }
                                                                binding.rcvRankCap3.apply {
                                                                    adapter = RankXAdapter(listCap3)
                                                                    layoutManager =
                                                                        LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                                                                    hasFixedSize()
                                                                }

                                                                Log.d(TAG, "allList 3: ${listCap3.size}")

                                                            }
                                                            override fun onCancelled(error: DatabaseError) {
                                                            }

                                                        })
                                                }

                                            }
                                            override fun onCancelled(error: DatabaseError) {
                                            }

                                        })
                                }
                            }
                            override fun onCancelled(error: DatabaseError) {
                            }

                        })
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }

        })
    }


    private fun showCap1() {
        binding.tvCap1.background = resources.getDrawable(R.drawable.border_button_b)
        binding.tvCap2.setBackgroundColor(resources.getColor(R.color.white))
        binding.tvCap3.setBackgroundColor(resources.getColor(R.color.white))

        binding.tvCap1.setTextColor(resources.getColor(R.color.white))
        binding.tvCap2.setTextColor(resources.getColor(R.color.black))
        binding.tvCap3.setTextColor(resources.getColor(R.color.black))

        binding.rcvRankCap1.visibility = View.VISIBLE
        binding.rcvRankCap2.visibility = View.GONE
        binding.rcvRankCap3.visibility = View.GONE
    }

    private fun showCap2() {
        binding.tvCap1.setBackgroundColor(resources.getColor(R.color.white))
        binding.tvCap2.background = resources.getDrawable(R.drawable.border_button_b)
        binding.tvCap3.setBackgroundColor(resources.getColor(R.color.white))

        binding.tvCap1.setTextColor(resources.getColor(R.color.black))
        binding.tvCap2.setTextColor(resources.getColor(R.color.white))
        binding.tvCap3.setTextColor(resources.getColor(R.color.black))

        binding.rcvRankCap1.visibility = View.GONE
        binding.rcvRankCap2.visibility = View.VISIBLE
        binding.rcvRankCap3.visibility = View.GONE
    }

    private fun showCap3() {
        binding.tvCap1.setBackgroundColor(resources.getColor(R.color.white))
        binding.tvCap2.setBackgroundColor(resources.getColor(R.color.white))
        binding.tvCap3.background = resources.getDrawable(R.drawable.border_button_b)

        binding.tvCap1.setTextColor(resources.getColor(R.color.black))
        binding.tvCap2.setTextColor(resources.getColor(R.color.black))
        binding.tvCap3.setTextColor(resources.getColor(R.color.white))

        binding.rcvRankCap1.visibility = View.GONE
        binding.rcvRankCap2.visibility = View.GONE
        binding.rcvRankCap3.visibility = View.VISIBLE
    }
}