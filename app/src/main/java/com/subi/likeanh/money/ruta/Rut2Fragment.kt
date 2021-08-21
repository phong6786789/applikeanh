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
import com.subi.likeanh.model.History
import com.subi.likeanh.model.Income
import com.subi.likeanh.model.User
import com.subi.likeanh.utils.LoadingDialog
import com.subi.nails2022.view.ShowDialog
import java.text.Format
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashMap


class Rut2Fragment : Fragment(), View.OnClickListener {
    private lateinit var binding: FragmentRut2Binding
    private val viewModel: Rut2ViewModel by viewModels()
    private var user = FirebaseAuth.getInstance().currentUser
    private lateinit var dialog: ShowDialog.Builder
    private lateinit var loading: LoadingDialog
    private val userDatabase =
        FirebaseDatabase.getInstance().getReference("user").child(user!!.uid)
    private val incomeDatabase =
        FirebaseDatabase.getInstance().getReference("income").child(user!!.uid)
    private val napRutDatabase =
        FirebaseDatabase.getInstance().getReference("rutnap")
    private val TG_PASSPORT_RESULT = 352

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
            userDatabase.addListenerForSingleValueEvent(object : ValueEventListener {
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
        loading = LoadingDialog.getInstance(requireContext())
        dialog = ShowDialog.Builder(requireContext())
        binding.setVariable(BR.viewModel, viewModel)
        checkForSetDataToUserFragment()
        requireActivity().findViewById<BottomNavigationView>(R.id.bottom_nav).visibility =
            View.VISIBLE
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btnConfirmDeposit -> {
                checkForDeposit()
            }
            R.id.btnCancelDeposit -> {
                moveToHomeFragment()
            }
        }
    }

    private fun moveToHomeFragment() {
        findNavController().navigate(R.id.homeFragment)
    }

    private fun updateTheUserPackage(totalMoney: String) {
        var userNameHashMap: HashMap<String, String> = HashMap<String, String>()
        userNameHashMap["totalMoney"] = totalMoney
        userDatabase.updateChildren(userNameHashMap as Map<String, Any>).addOnSuccessListener {


        }.addOnFailureListener {
            Log.d("kienda", "updateTheUserPackage: + ${it.message}")
        }
    }

    fun checkTheAvailableTime(number: Int, currentTime: Long, money: Long, moneyDeposit: String) {

        when (number) {
            1 -> {
                val availableTime = System.currentTimeMillis() - currentTime
                val checkTime = (availableTime / (1000 * 60 * 60 * 24))
                Log.d(TAG, "onDataChange: $checkTime")
                if (checkTime <= 3) {
                    checkForAddToHistory(moneyDeposit)
                    Log.d("mmm", "checkTheAvailableTime: ")
                    return
                }
                return
            }
            2 -> {
                val availableTime = System.currentTimeMillis() - currentTime
                val checkTime = (availableTime / (1000 * 60 * 60 * 24))
                if (checkTime <= 7) {
                    checkForAddToHistory(moneyDeposit)
                    return
                }
                return
            }
            3 -> {
                val availableTime = System.currentTimeMillis() - currentTime
                val checkTime = (availableTime / (1000 * 60 * 60 * 24))
                if (checkTime <= 20) {
                    checkForAddToHistory(moneyDeposit)
                    return
                }
                return
            }
            4 -> {
                val availableTime = System.currentTimeMillis() - currentTime
                val checkTime = (availableTime / (1000 * 60 * 60 * 24))
                if (checkTime <= 30) {
                    checkForAddToHistory(moneyDeposit)
                    return
                }
                return
            }
            5 -> {
                val availableTime = System.currentTimeMillis() - currentTime
                val checkTime = (availableTime / (1000 * 60 * 60 * 24))
                if (checkTime <= 40) {
                    checkForAddToHistory(moneyDeposit)
                    return
                }
                return
            }
            6 -> {
                val availableTime = System.currentTimeMillis() - currentTime
                val checkTime = (availableTime / (1000 * 60 * 60 * 24))
                if (checkTime <= 50) {
                    checkForAddToHistory(moneyDeposit)
                    return
                }
                return
            }
            7 -> {
                val availableTime = System.currentTimeMillis() - currentTime
                val checkTime = (availableTime / (1000 * 60 * 60 * 24))
                if (checkTime <= 68) {
                    checkForAddToHistory(moneyDeposit)
                    return
                }
                return
            }
        }

    }


    private fun checkForDeposit() {
        if (user != null) {
            userDatabase.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (binding.edtMoneyToDeposit.text.toString().isNotEmpty()) {
                        val user = snapshot.getValue(User::class.java)
                        val moneyDeposit = user?.totalMoney.toString()
                            .toLong() - binding.edtMoneyToDeposit.text.toString().toLong()

                        if (moneyDeposit >= 0) {
                            checkTheAvailableTime(
                                user?.userPackage?.toInt()!!,
                                user.transferTime.toLong(),
                                moneyDeposit,
                                binding.edtMoneyToDeposit.text.toString()
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

    private fun addToInComeDatabase(
        value: String,
        userName: String,
        userMoney: String,
        currentIndex: String
    ) {
        napRutDatabase.child(user!!.uid + "rut")
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val rut = snapshot.getValue(Income::class.java)
                    if (rut == null) {
                        val inCome = History(
                            userName,
                            userMoney,
                            convertTime(System.currentTimeMillis()),
                            "Rut", "False"
                        )
                        incomeDatabase.child(value).setValue(inCome)
                        napRutDatabase.child(user!!.uid + "rut").setValue(inCome)
                        updateTheIndexOfTheUser(currentIndex)
                        dialog.show(
                            "Giao dịch thành công",
                            "Giao dịch thành công admin đang thực hiện yêu cầu của bạn, vui lòng chờ đợi"
                        )
                        moveToHomeFragment()
                        return
                    }
                    dialog.show(
                        "Giao dịch thất bại",
                        "Giao dịch cũ của bạn đang được admin xử lý, vui lòng chờ đợi"
                    )
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })


    }

    private fun convertTime(time: Long): String {
        val date = Date(time)
        val format: Format = SimpleDateFormat("dd-M-yyyy hh:mm:ss")
        return format.format(date)
    }

    private fun updateTheIndexOfTheUser(index: String) {
        var userNameHashMap: HashMap<String, String> = HashMap<String, String>()
        userNameHashMap["index"] = index
        userDatabase.updateChildren(userNameHashMap as Map<String, Any>).addOnSuccessListener {
        }.addOnFailureListener {

        }
    }


    private fun checkForAddToHistory(userMoney: String) {
        if (user != null) {
            userDatabase.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val user = snapshot.getValue(User::class.java)
                    val currentIndex = user?.index!!.toInt() + 1
                    addToInComeDatabase(user.index, user.name, userMoney, currentIndex.toString())
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