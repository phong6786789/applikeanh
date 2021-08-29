package com.subi.likeanh.rank

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.subi.likeanh.BR
import com.subi.likeanh.R
import com.subi.likeanh.adapter.RankAdapter
import com.subi.likeanh.callback.OnItemUserClick

import com.subi.likeanh.databinding.FragmentRankBinding
import com.subi.likeanh.model.User
import com.subi.likeanh.utils.LoadingDialog
import com.subi.nails2022.view.ShowDialog

class RankFragment : Fragment(), OnItemUserClick, View.OnClickListener {
    private lateinit var binding: FragmentRankBinding
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
    private var rankAdapterCap1: RankAdapter? = null
    private var rankAdapterCap2: RankAdapter? = null
    private var rankAdapterCap3: RankAdapter? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentRankBinding.inflate(inflater, container, false)
        init()
        initRecyclerView()
        initDataForRecyclerViewCap1()
        setOnClickForViews()
        return binding.root;
    }

    private fun setOnClickForViews() {
        binding.tvCap1.setOnClickListener(this)
        binding.tvCap2.setOnClickListener(this)
        binding.tvCap3.setOnClickListener(this)
    }

    private fun initRecyclerView() {
        rankAdapterCap1 = RankAdapter(listCap1, this)
        rankAdapterCap2 = RankAdapter(listCap2, this)
        rankAdapterCap3 = RankAdapter(listCap3, this)
        binding.apply {
            rcvRankCap1.apply {
                adapter = rankAdapterCap1
                layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                hasFixedSize()
            }
            rcvRankCap2.apply {
                adapter = rankAdapterCap2
                layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                hasFixedSize()
            }
            rcvRankCap3.apply {
                adapter = rankAdapterCap3
                layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                hasFixedSize()
            }
        }
    }

    private fun initDataForRecyclerViewCap1() {
        binding.rcvRankCap1.visibility = View.VISIBLE
        binding.rcvRankCap2.visibility = View.GONE
        binding.rcvRankCap3.visibility = View.GONE
        phoneDatabase.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val data = arrayListOf<String>()
                for (value in snapshot.children) {
                    data.add(value.key.toString())
                }

                userDatabase.addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        val userData = arrayListOf<User>()
                        for (value in snapshot.children) {
                            val currentUser = value.getValue(User::class.java)
                            if (data.contains(currentUser?.phone)) {
                                userData.add(currentUser!!)
                            }
                        }
                        rankAdapterCap1?.setNewData(userData)
                        Log.d(
                            TAG, "onDataChange: Data cap 1 ${
                                data.size
                            }"
                        )
                    }

                    override fun onCancelled(error: DatabaseError) {

                    }

                })


            }

            override fun onCancelled(error: DatabaseError) {

            }

        })

    }


    companion object {
        private const val TAG = "KienDA"
    }

    fun init() {
        binding.setVariable(BR.viewModel, viewModel)
        requireActivity().findViewById<BottomNavigationView>(R.id.bottom_nav).visibility =
            View.VISIBLE
        binding.tvCap2.setBackgroundColor(resources.getColor(R.color.white))
        binding.tvCap3.setBackgroundColor(resources.getColor(R.color.white))
    }

    override fun onShortClick(position: Int, user: User) {
        val bundle = bundleOf("userToUserDetailFragment" to user)
        findNavController().navigate(R.id.rankDetailFragment, bundle)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.tvCap1 -> {
                showCap1()
            }
            R.id.tvCap2 -> {
                showCap2()
            }
            R.id.tvCap3 -> {
                showCap3()
            }
        }
    }

    private fun showCap1() {
        binding.tvCap1.background = resources.getDrawable(R.drawable.border_button_b)
        binding.tvCap2.setBackgroundColor(resources.getColor(R.color.white))
        binding.tvCap3.setBackgroundColor(resources.getColor(R.color.white))

        binding.rcvRankCap1.visibility = View.VISIBLE
        binding.rcvRankCap2.visibility = View.GONE
        binding.rcvRankCap3.visibility = View.GONE
    }

    private fun showCap2() {
        binding.tvCap1.setBackgroundColor(resources.getColor(R.color.white))
        binding.tvCap2.background = resources.getDrawable(R.drawable.border_button_b)
        binding.tvCap3.setBackgroundColor(resources.getColor(R.color.white))

        binding.rcvRankCap1.visibility = View.GONE
        binding.rcvRankCap2.visibility = View.VISIBLE
        binding.rcvRankCap3.visibility = View.GONE
    }

    private fun showCap3() {
        binding.tvCap1.setBackgroundColor(resources.getColor(R.color.white))
        binding.tvCap2.setBackgroundColor(resources.getColor(R.color.white))
        binding.tvCap3.background = resources.getDrawable(R.drawable.border_button_b)


        binding.rcvRankCap1.visibility = View.GONE
        binding.rcvRankCap2.visibility = View.GONE
        binding.rcvRankCap3.visibility = View.VISIBLE
    }
}