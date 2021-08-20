package com.subi.likeanh.money.nap

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.subi.likeanh.BR


import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.subi.likeanh.R
import com.subi.likeanh.databinding.FragmentNapBinding
import com.subi.likeanh.model.User
import com.subi.likeanh.utils.LoadingDialog
import com.subi.nails2022.view.DialogLeftInterface
import com.subi.nails2022.view.DialogRightInterface
import com.subi.nails2022.view.ShowDialog


class NapFragment : Fragment(), View.OnClickListener, DialogRightInterface {
    private lateinit var binding: FragmentNapBinding
    private val viewModel: NapViewModel by viewModels()
    private var user = FirebaseAuth.getInstance().currentUser
    private val ref =
        FirebaseDatabase.getInstance().getReference("user").child(user!!.uid)
    private lateinit var loading: LoadingDialog
    private lateinit var dialog: ShowDialog.Builder

    private val incomeDatabase =
        FirebaseDatabase.getInstance().getReference("income").child(user!!.uid)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentNapBinding.inflate(inflater, container, false)
        init()
        checkForSpinner()
        checkForSetDataToUserFragment()
        setOnClickForViews()
        return binding.root;
    }

    private fun setOnClickForViews() {
        binding.btnNapTien.setOnClickListener(this)
    }



    private fun checkForSetDataToUserFragment() {
        if (user != null) {
            val ref =
                FirebaseDatabase.getInstance().getReference("user").child(user!!.uid)
            ref.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val user = snapshot.getValue(User::class.java)
                    viewModel.user.set(user)


                    if (user?.bank!!.isEmpty() || user.stk.isEmpty()) {
                        showDialogToFillTheTheInformation()
                    }

                }

                override fun onCancelled(error: DatabaseError) {
                }

            })
        }
    }


    private fun showDialogToFillTheTheInformation() {
        var dialog: Dialog? = null
        dialog = ShowDialog.Builder(requireContext())
            .title("Bạn chưa điền đầy đủ thông tin cá nhân")
            .message("Bạn có muốn cập nhật thông tin tài khoản không")
            .setRightButton("KHÔNG", object : DialogRightInterface {
                override fun onClick() {
                    dialog?.dismiss()
                }
            })
            .setLeftButton("CÓ", object : DialogLeftInterface {
                override fun onClick() {
                    findNavController().navigate(R.id.napCofirmFragment)
                    dialog?.dismiss()
                }
            })
            .confirm()
        dialog?.show()
    }

    private fun updateTheUserPackage() {
        var userNameHashMap: HashMap<String, String> = HashMap<String, String>()
        userNameHashMap["userPackage"] = binding.tvTengoi.text.toString()
        ref.updateChildren(userNameHashMap as Map<String, Any>).addOnSuccessListener {
            findNavController().navigate(R.id.napCofirmFragment)
        }.addOnFailureListener {
            Log.d("kienda", "updateTheUserPackage: + ${it.message}")
        }
    }

    private fun checkForSpinner() {
        binding.apply {
            spGoitk.apply {
                adapter = ArrayAdapter(
                    requireContext(),
                    android.R.layout.simple_dropdown_item_1line,
                    viewModel!!.listLoai
                )

                onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        parent: AdapterView<*>?,
                        view: View?,
                        position: Int,
                        id: Long,
                    ) {
                        viewModel?.apply {
                            var pos = 0
                            when (position) {
                                0 -> {
                                    money.set(list[0])
                                    pos = 1
                                }
                                1 -> {
                                    money.set(list[1])
                                    pos = 2
                                }
                                2 -> {
                                    money.set(list[2])
                                    pos = 3
                                }
                                3 -> {
                                    money.set(list[3])
                                    pos = 4
                                }
                                4 -> {
                                    money.set(list[4])
                                    pos = 5
                                }
                                5 -> {
                                    money.set(list[5])
                                    pos = 6
                                }
                                else -> {
                                    money.set(list[6])
                                    pos = 7
                                }
                            }
                            tvInfo.setText("Chi tiết gói $pos")
                        }

                    }

                    override fun onNothingSelected(parent: AdapterView<*>?) {
                        viewModel?.money?.set(viewModel?.list?.get(0))
                        tvInfo.setText("Chi tiết gói 1")
                    }
                }
            }

        }
    }

    private fun checkForStartScreen() {
        if (user != null) {
            val ref =
                FirebaseDatabase.getInstance().getReference("user").child(user!!.uid)
            ref.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val user = snapshot.getValue(User::class.java)
                    viewModel.user.set(user)

                    if (user?.userPackage?.toInt() != 0) {
                        dialog.show(
                            "Bạn không đủ điền kiện để nạp vì bạn đã đăng ký 1 gói",
                            ""
                        )
                        return
                    }
                    if (user?.timesIntroduce?.toInt()!! >= 10) {
                        updateTheUserPackage()
                        return
                    }
                    dialog.show(
                        "Bạn không đủ điền kiện để nạp vì lượt giới thiệu của bạn <=10",
                        ""
                    )
                }

                override fun onCancelled(error: DatabaseError) {
                }

            })
        }
    }

    fun init() {
        binding.setVariable(BR.viewModel, viewModel)
        loading = LoadingDialog.getInstance(requireContext())
        dialog = ShowDialog.Builder(requireContext())
        requireActivity().findViewById<BottomNavigationView>(R.id.bottom_nav).visibility =
            View.VISIBLE
        viewModel.load()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btnNapTien -> {
                checkForStartScreen()
            }
        }
    }


    override fun onClick() {

    }
}