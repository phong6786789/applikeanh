package com.subi.likeanh.rank

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.subi.likeanh.BR
import com.subi.likeanh.R
import com.subi.likeanh.adapter.HomeAdapter
import com.subi.likeanh.adapter.LichSuAdapter
import com.subi.likeanh.adapter.RankAdapter
import com.subi.likeanh.databinding.FragmentHomeBinding
import com.subi.likeanh.databinding.FragmentRankBinding
import com.subi.likeanh.home.HomeViewModel
import com.subi.likeanh.model.History
import com.subi.likeanh.model.User
import com.subi.likeanh.money.lichsu.LichSuFragment
import com.subi.likeanh.utils.LoadingDialog
import com.subi.likeanh.utils.Utils
import com.subi.nails2022.view.ShowDialog

class RankFragment : Fragment() {
    private lateinit var binding: FragmentRankBinding
    private val viewModel: RankViewModel by viewModels()
    private var user = FirebaseAuth.getInstance().currentUser
    private lateinit var dialog: ShowDialog.Builder
    private lateinit var loading: LoadingDialog
    private val userDatabase =
        FirebaseDatabase.getInstance().getReference("user")
    private var list = arrayListOf<User>()
    private var rankAdapter: RankAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentRankBinding.inflate(inflater, container, false)
        init()
        initRecyclerViews()



        return binding.root;
    }

    private fun initRecyclerViews() {
        val list = arrayListOf<User>()
        checkForSetDataToUserFragment()
    }

    private fun checkForSetDataToUserFragment() {
        if (user != null) {
            userDatabase.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val list = mutableListOf<User>()
                    for (data in snapshot.children) {
                        val user = data.getValue(User::class.java)
                        list.add(user!!)
                    }

                    if (list.isEmpty()) {
                        return
                    }

                    list.sortByDescending {
                        it.totalMoney.toLong()
                    }

                    Log.d(TAG, "onDataChange: ${list.size}")

                    when (list.size) {
                        1 -> {
                            binding.tvTop1.text = list[0].name

                            binding.moneyTop1.text = Utils.getFMoney(list[0].totalMoney)
                        }
                        2 -> {
                            binding.tvTop1.text = Utils.getFMoney(list[0].name)
                            binding.tvTop2.text = Utils.getFMoney(list[1].name)

                            binding.moneyTop1.text = Utils.getFMoney(list[0].totalMoney)
                            binding.moneyTop2.text = Utils.getFMoney(list[1].totalMoney)

                        }
                        else -> {
                            binding.tvTop1.text = list[0].name
                            binding.tvTop2.text = list[1].name
                            binding.tvTop3.text = list[2].name

                            binding.moneyTop1.text = Utils.getFMoney(list[0].totalMoney)
                            binding.moneyTop2.text = Utils.getFMoney(list[1].totalMoney)
                            binding.moneyTop3.text = Utils.getFMoney(list[2].totalMoney)
                        }
                    }

                    if (list.size >= 3) {
                        list.removeAt(0)
                        list.removeAt(1)
                        list.removeAt(2)
                    }

                    rankAdapter = RankAdapter(list)
                    binding.apply {
                        rcv.apply {
                            adapter = rankAdapter
                            layoutManager =
                                LinearLayoutManager(
                                    requireContext(),
                                    LinearLayoutManager.VERTICAL,
                                    false
                                )
                            hasFixedSize()
                            scheduleLayoutAnimation()
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {

                }
            })
        }
    }

    companion object {
        private const val TAG = "KienDA"
    }


    fun init() {
        binding.setVariable(BR.viewModel, viewModel)
        requireActivity().findViewById<BottomNavigationView>(R.id.bottom_nav).visibility =
            View.VISIBLE
    }
}