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
import com.subi.likeanh.model.*
import com.subi.likeanh.utils.LoadingDialog
import com.subi.likeanh.utils.SendTelegram
import com.subi.likeanh.utils.Utils
import com.subi.nails2022.view.ShowDialog
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
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
    var dataFullUser: User? = null
    private val userDatabase =
        FirebaseDatabase.getInstance().getReference("user").child(user!!.uid)
    private val incomeDatabase =
        FirebaseDatabase.getInstance().getReference("income").child(user!!.uid)
    private val napRutDatabase =
        FirebaseDatabase.getInstance().getReference("rutnap").child(Utils.getUID() + "rut")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentRut2Binding.inflate(inflater, container, false)
        init()

        //Check n???p
        napRutDatabase.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                //C?? th??? r??t v?? n???p n???u status true
                if (snapshot.child("status").getValue(Boolean::class.java) == true) {
                    checkForSetDataToUserFragment()
                    setOnClickForViews()
                } else {
                    dialog.show(
                        "Th??ng b??o",
                        "B???n ???? c?? y??u c???u r??t, vui l??ng ?????i x??? l?? tr?????c khi ti???p t???c th???c hi???n giao d???ch m???i!"
                    )
                    requireActivity().onBackPressed()
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }

        })

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
                    dataFullUser = user
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

    private fun updateTotalMoney(totalMoney: String) {
        var userNameHashMap: HashMap<String, String> = HashMap<String, String>()
        userNameHashMap["totalMoney"] = totalMoney
        userDatabase.updateChildren(userNameHashMap as Map<String, Any>).addOnSuccessListener {

        }.addOnFailureListener {
            Log.d("kienda", "updateTheUserPackage: + ${it.message}")
        }
    }

    //Check h???n r??tti???n
    private fun checkTheAvailableTime(userPackage: Int, currentTime: Long, moneyDeposit: String) {
        when (userPackage) {
            1 -> {
                val availableTime = System.currentTimeMillis() - currentTime
                val checkTime = (availableTime / (1000 * 60 * 60 * 24))
                if (checkTime >= 3) {
                    checkForAddToHistory(moneyDeposit)
                    return
                }
                return
            }
            2 -> {
                val availableTime = System.currentTimeMillis() - currentTime
                val checkTime = (availableTime / (1000 * 60 * 60 * 24))
                if (checkTime >= 7) {
                    checkForAddToHistory(moneyDeposit)
                    return
                }
                return
            }
            3 -> {
                val availableTime = System.currentTimeMillis() - currentTime
                val checkTime = (availableTime / (1000 * 60 * 60 * 24))
                if (checkTime >= 20) {
                    checkForAddToHistory(moneyDeposit)
                    return
                }
                return
            }
            4 -> {
                val availableTime = System.currentTimeMillis() - currentTime
                val checkTime = (availableTime / (1000 * 60 * 60 * 24))
                if (checkTime >= 30) {
                    checkForAddToHistory(moneyDeposit)
                    return
                }
                return
            }
            5 -> {
                val availableTime = System.currentTimeMillis() - currentTime
                val checkTime = (availableTime / (1000 * 60 * 60 * 24))
                if (checkTime >= 40) {
                    checkForAddToHistory(moneyDeposit)
                    return
                }
                return
            }
            6 -> {
                val availableTime = System.currentTimeMillis() - currentTime
                val checkTime = (availableTime / (1000 * 60 * 60 * 24))
                if (checkTime >= 50) {
                    checkForAddToHistory(moneyDeposit)
                    return
                }
                return
            }
            7 -> {
                val availableTime = System.currentTimeMillis() - currentTime
                val checkTime = (availableTime / (1000 * 60 * 60 * 24))
                if (checkTime >= 68) {
                    checkForAddToHistory(moneyDeposit)
                    return
                }
                return
            }
        }

    }


    private fun checkForDeposit() {
        if (user != null) {
            val mon = binding.edtMoneyToDeposit.text.toString()
            if (mon.isNotEmpty()) {
                //Check kh??c 0 m???i kh??? d???ng ????? r??t
                if (dataFullUser?.userPackage?.contains("0") == true) {
                    //check ti???n r??t kh??? d???ng
                    if (mon.toLong() <= dataFullUser?.totalMoney?.toLong() ?: 0L) {
                        addToInComeDatabase(dataFullUser!!, mon)
                    } else {
                        Toast.makeText(context, "S??? ti???n r??t qu?? h???n m???c", Toast.LENGTH_SHORT)
                            .show()
                    }
                } else {
                    Toast.makeText(context, "Ch??a ?????n h???n ????? r??t ti???n", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(context, "Vui l??ng nh???p s??? ti???n c???n r??t", Toast.LENGTH_SHORT).show()
                checkTheAvailableTime(
                    dataFullUser?.userPackage!!.toInt(),
                    dataFullUser?.currentDate!!.toLong(),
                    binding.edtMoneyToDeposit.text.toString()
                )
            }


//            userDatabase.addListenerForSingleValueEvent(object : ValueEventListener {
//                override fun onDataChange(snapshot: DataSnapshot) {
//                    if (binding.edtMoneyToDeposit.text.toString().isNotEmpty()) {
//                        val user = snapshot.getValue(User::class.java)
//                        val moneyDeposit = user?.totalMoney.toString()
//                            .toLong() - binding.edtMoneyToDeposit.text.toString().toLong()
//
//                        if (moneyDeposit >= 0) {
//                            checkTheAvailableTime(
//                                user?.userPackage?.toInt()!!,
//                                user.transferTime.toLong(),
//                                moneyDeposit,
//                                binding.edtMoneyToDeposit.text.toString()
//                            )
//                            Log.d(TAG, "onDataChange: $moneyDeposit")
//                            return
//                        }
//                        Toast.makeText(context,
//                            "Ch??a ?????n h???n ????? r??t ho???c kh??ng ????? ti???n r??t!",
//                            Toast.LENGTH_SHORT).show()
//                        return
//                    }
//                    Log.d(TAG, "onDataChange:Kh??ng ???????c b??? tr???ng th??ng tin")
//                    Toast.makeText(context, "Vui l??ng nh???p s??? ti???n c???n r??t", Toast.LENGTH_SHORT)
//                        .show()
//                }
//
//                override fun onCancelled(error: DatabaseError) {
//                }
//
//            })
        }

    }

    //G???i y??u c???u r??t ti???n v?? tr???ng th??i
    private fun addToInComeDatabase(
        user: User,
        tienrut: String,
    ) {
        val naprut = NapRut(
            Utils.getUID(),
            tienrut,
            user.userPackage,
            System.currentTimeMillis().toString(),
            true,
            false
        )

        //B???n noti telegram
        napRutDatabase.setValue(naprut).addOnCompleteListener {
            val body = "===Y??U C???U R??T TI???N==     " +
                    "T??n: ${user.name.toUpperCase()}" +
                    "\n STK: ${user.stk}" +
                    "\n Ng??n h??ng: ${user.bank.toUpperCase()}" +
                    "\n S??? ti???n y??u c???u r??t: $tienrut"
            GlobalScope.launch {
                SendTelegram.send(body)
                requireActivity().runOnUiThread {
                    dialog.show(
                        "Giao d???ch th??nh c??ng",
                        "Admin ??ang x??? l?? y??u c???u c???a b???n, vui l??ng ch??? ?????i"
                    )
                    moveToHomeFragment()
                }
            }
        }

//        napRutDatabase.child(user!!.uid + "rut")
//            .addListenerForSingleValueEvent(object : ValueEventListener {
//                override fun onDataChange(snapshot: DataSnapshot) {
//                    val rut = snapshot.getValue(Income::class.java)
//                    if (rut == null) {
//                        val inCome = History(
//                            userName,
//                            userMoney,
//                            convertTime(System.currentTimeMillis()),
//                            "Rut", "False"
//                        )
//                        incomeDatabase.child(value).setValue(inCome)
//                        napRutDatabase.child(user!!.uid + "rut").setValue(inCome)
//                        updateTheIndexOfTheUser(currentIndex)
//                        dialog.show(
//                            "Giao d???ch th??nh c??ng",
//                            "Admin ??ang x??? l?? y??u c???u c???a b???n, vui l??ng ch??? ?????i"
//                        )
//                        moveToHomeFragment()
//                        return
//                    }
//                    dialog.show(
//                        "Giao d???ch th???t b???i",
//                        "Giao d???ch c?? c???a b???n ??ang ???????c admin x??? l??, vui l??ng ch??? ?????i"
//                    )
//                }
//
//                override fun onCancelled(error: DatabaseError) {
//                    TODO("Not yet implemented")
//                }
//
//            })


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


    private fun checkForAddToHistory(tienRut: String) {
        if (user != null) {
            userDatabase.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val user = snapshot.getValue(User::class.java)
                    user?.let { addToInComeDatabase(it, tienRut) }
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