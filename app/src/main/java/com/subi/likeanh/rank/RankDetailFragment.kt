package com.subi.likeanh.rank

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
import com.subi.likeanh.adapter.RankAdapter
import com.subi.likeanh.callback.OnItemUserClick
import com.subi.likeanh.databinding.FragmentRankBinding
import com.subi.likeanh.databinding.FragmentRankDetailBinding
import com.subi.likeanh.model.User
import com.subi.likeanh.utils.LoadingDialog
import com.subi.nails2022.view.ShowDialog

class RankDetailFragment : Fragment(), OnItemUserClick {
    private lateinit var binding: FragmentRankDetailBinding
    private val viewModel: RankViewModel by viewModels()
    private var user = FirebaseAuth.getInstance().currentUser
    private lateinit var dialog: ShowDialog.Builder
    private lateinit var loading: LoadingDialog
    private val userDatabase =
        FirebaseDatabase.getInstance().getReference("user")
    private val phoneDatabase =
        FirebaseDatabase.getInstance().getReference("sdtGT")
    private var list = arrayListOf<User>()
    private var rankAdapter: RankAdapter? = null
    private lateinit var currentUser: User


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentRankDetailBinding.inflate(inflater, container, false)
        init()
        initRecyclerView()
        initData()
        initDataForRecyclerView()


        return binding.root;
    }

    private fun initData() {
        arguments?.let { bundle ->
            if (bundle != null) {
                currentUser = bundle.getParcelable("userToUserDetailFragment")!!
                binding.user = currentUser
                Log.d(TAG, "initData User : ${currentUser?.phone}")
            }
        }
    }

    private fun initRecyclerView() {
        rankAdapter = RankAdapter(list, this)
        binding.apply {
            rcvRank.apply {
                adapter = rankAdapter
                layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                hasFixedSize()
            }
        }
    }

    private fun initDataForRecyclerView() {
        phoneDatabase.child(currentUser.phone)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val data = arrayListOf<String>()
                    for (value in snapshot.children) {
                        data.add(value.key.toString())
                    }
                    Log.d(TAG, "onDataChangeM: ${data.size}")


                    userDatabase.addListenerForSingleValueEvent(object : ValueEventListener {
                        val userData = arrayListOf<User>()
                        override fun onDataChange(snapshot: DataSnapshot) {
                            val userData = arrayListOf<User>()
                            for (value in snapshot.children) {
                                val currentUser = value.getValue(User::class.java)
                                if (data.contains(currentUser?.phone)) {
                                    userData.add(currentUser!!)
                                }
                            }
                            rankAdapter?.setNewData(userData)

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
    }

    override fun onShortClick(position: Int, user: User) {
        TODO("Not yet implemented")
    }


}