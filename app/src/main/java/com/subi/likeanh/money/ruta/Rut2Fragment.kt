package com.subi.likeanh.money.ruta

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.subi.likeanh.BR
import com.subi.likeanh.R
import com.subi.likeanh.databinding.FragmentRut2Binding
import com.subi.likeanh.model.User
import com.subi.likeanh.utils.LoadingDialog
import com.subi.nails2022.view.ShowDialog


class Rut2Fragment : Fragment(), View.OnClickListener {
    private lateinit var binding: FragmentRut2Binding
    private val viewModel: Rut2ViewModel by viewModels()
    private var user = FirebaseAuth.getInstance().currentUser
    private lateinit var dialog: ShowDialog.Builder
    private lateinit var loading: LoadingDialog
    private val ref =
        FirebaseDatabase.getInstance().getReference("user").child(user!!.uid)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentRut2Binding.inflate(inflater, container, false)
        init()
        setOnClickForViews()
        binding.apply {

        }

        return binding.root;
    }

    private fun setOnClickForViews() {
        binding.btnCancelDeposit.setOnClickListener(this)
        binding.btnConfirmDeposit.setOnClickListener(this)
    }

    private fun checkForSetDataToUserFragment() {
        if (user != null) {
            ref.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val user = snapshot.getValue(User::class.java)
                    viewModel.user.set(user)
                }
                override fun onCancelled(error: DatabaseError) {
                }
            })
        }
    }

    fun init() {
        binding.setVariable(BR.viewModel, viewModel)
        checkForSetDataToUserFragment()
        requireActivity().findViewById<BottomNavigationView>(R.id.bottom_nav).visibility =
            View.VISIBLE
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btnConfirmDeposit -> {
                Toast.makeText(activity, "kienda", Toast.LENGTH_SHORT).show()
                checkForDeposit()
            }
            R.id.btnCancelDeposit -> {
                moveToMoneyFragment()
            }
        }
    }

    private fun moveToMoneyFragment() {

    }

    private fun updateTheUserPackage(totalMoney: String) {
        var userNameHashMap: HashMap<String, String> = HashMap<String, String>()
        userNameHashMap["totalMoney"] = totalMoney
        ref.updateChildren(userNameHashMap as Map<String, Any>).addOnSuccessListener {
            findNavController().navigate(R.id.napCofirmFragment)
        }.addOnFailureListener {
            Log.d("kienda", "updateTheUserPackage: + ${it.message}")
        }
    }

    fun checkTheAvailableTime(number: Int, currentTime: Long, money: Int) {

        when (number) {
            1 -> {
                val availableTime = System.currentTimeMillis() - currentTime
                val checkTime = (availableTime / (1000 * 60 * 60 * 24))
                Log.d(TAG, "onDataChange: $checkTime")
                if (checkTime <= 3) {
                    updateTheUserPackage(money.toString())
                    checkForSetDataToUserFragment()
                    Log.d("mmm", "checkTheAvailableTime: ")
                    return
                }
                return
            }
            2 -> {
                val availableTime = System.currentTimeMillis() - currentTime
                val checkTime = (availableTime / (1000 * 60 * 60 * 24))
                if (checkTime <= 7) {
                    updateTheUserPackage(money.toString())
                    checkForSetDataToUserFragment()
                    Toast.makeText(context, "Bạn đủ đk thanh toán", Toast.LENGTH_SHORT).show()
                    return
                }
                return
            }
            3 -> {
                val availableTime = System.currentTimeMillis() - currentTime
                val checkTime = (availableTime / (1000 * 60 * 60 * 24))
                if (checkTime <= 20) {
                    updateTheUserPackage(money.toString())
                    checkForSetDataToUserFragment()
                    Toast.makeText(context, "Bạn đủ đk thanh toán", Toast.LENGTH_SHORT).show()
                    return
                }
                return
            }
            4 -> {
                val availableTime = System.currentTimeMillis() - currentTime
                val checkTime = (availableTime / (1000 * 60 * 60 * 24))
                if (checkTime <= 30) {
                    updateTheUserPackage(money.toString())
                    checkForSetDataToUserFragment()
                    Toast.makeText(context, "Bạn đủ đk thanh toán", Toast.LENGTH_SHORT).show()
                    return
                }
                return
            }
            5 -> {
                val availableTime = System.currentTimeMillis() - currentTime
                val checkTime = (availableTime / (1000 * 60 * 60 * 24))
                if (checkTime <= 40) {
                    updateTheUserPackage(money.toString())
                    checkForSetDataToUserFragment()
                    Toast.makeText(context, "Bạn đủ đk thanh toán", Toast.LENGTH_SHORT).show()
                    return
                }
                return
            }
            6 -> {
                val availableTime = System.currentTimeMillis() - currentTime
                val checkTime = (availableTime / (1000 * 60 * 60 * 24))
                if (checkTime <= 50) {
                    updateTheUserPackage(money.toString())
                    checkForSetDataToUserFragment()
                    Toast.makeText(context, "Bạn đủ đk thanh toán", Toast.LENGTH_SHORT).show()
                    return
                }
                return
            }
            7 -> {
                val availableTime = System.currentTimeMillis() - currentTime
                val checkTime = (availableTime / (1000 * 60 * 60 * 24))
                if (checkTime <= 68) {
                    updateTheUserPackage(money.toString())
                    checkForSetDataToUserFragment()
                    return
                }
                return
            }
        }

    }

    private fun checkForDeposit() {
        if (user != null) {
            ref.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (binding.edtMoneyToDeposit.text.toString().isNotEmpty()) {
                        val user = snapshot.getValue(User::class.java)
                        val moneyDeposit = user?.totalMoney.toString()
                            .toInt() - binding.edtMoneyToDeposit.text.toString().toInt()

                        if (moneyDeposit >= 0) {
                            checkTheAvailableTime(
                                user?.userPackage?.toInt()!!,
                                user.transferTime.toLong(),
                                moneyDeposit
                            )
                            Log.d(TAG, "onDataChange: $moneyDeposit")
                            return
                        }
                        Log.d(TAG, "onDataChange: khong hope le")
                        Toast.makeText(context, "Khong hop le", Toast.LENGTH_SHORT).show()
                        return
                    }
                    Log.d(TAG, "onDataChange:Không được bỏ trống thông tin")
                    Toast.makeText(context, "Không được bỏ trống thông tin", Toast.LENGTH_SHORT)
                        .show()
                }

                override fun onCancelled(error: DatabaseError) {
                }

            })

        }

    }

    companion object {
        private const val TAG = "mmm"
    }
}