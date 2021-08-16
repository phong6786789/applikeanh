package com.subi.likeanh.money.nap

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.subi.likeanh.BR
import com.subi.likeanh.R
import com.subi.likeanh.databinding.FragmentHomeBinding
import com.subi.likeanh.databinding.FragmentMoneyBinding
import com.subi.likeanh.databinding.FragmentNapBinding
import com.subi.likeanh.home.HomeViewModel
import com.subi.likeanh.money.MoneyViewModel

class NapFragment : Fragment() {
    lateinit var binding: FragmentNapBinding
    private val viewModel: NapViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentNapBinding.inflate(inflater, container, false)
        init()
        binding.apply {
            val listLoai = arrayListOf(
                "Gói 1", "Gói 2", "Gói 3", "Gói 4", "Gói 5", "Gói 6", "Gói 7"
            )
            spGoitk.apply {
                adapter = ArrayAdapter(requireContext(),
                    android.R.layout.simple_dropdown_item_1line,
                    listLoai)

                onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        parent: AdapterView<*>?,
                        view: View?,
                        position: Int,
                        id: Long,
                    ) {
                        print("item: $position")
                        viewModel?.apply {
                            money.set(
                                when (position) {
                                    0 -> {
                                        list[0]
                                    }
                                    1 -> list[1]
                                    2 -> list[2]
                                    3 -> list[3]
                                    4 -> list[4]
                                    5 -> list[5]
                                    6 -> list[6]
                                    else -> list[0]
                                }
                            )
                        }

                    }

                    override fun onNothingSelected(parent: AdapterView<*>?) {
                        viewModel?.money?.set(viewModel?.list?.get(0))
                    }

                }
            }

        }

        return binding.root;
    }

    fun init() {
        binding.setVariable(BR.viewModel, viewModel)
        requireActivity().findViewById<BottomNavigationView>(R.id.bottom_nav).visibility =
            View.VISIBLE
        viewModel.load()
    }
}