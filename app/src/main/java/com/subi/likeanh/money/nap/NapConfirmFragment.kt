package com.subi.likeanh.money.nap

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import com.subi.likeanh.databinding.FragmentNapCofirmBinding
import com.subi.likeanh.model.*
import com.subi.likeanh.utils.LoadingDialog
import com.subi.likeanh.utils.SendTelegram
import com.subi.likeanh.utils.Utils.getUID
import com.subi.nails2022.view.DialogLeftInterface
import com.subi.nails2022.view.DialogRightInterface
import com.subi.nails2022.view.ShowDialog
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.text.Format
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashMap

class NapConfirmFragment : Fragment(), View.OnClickListener {
    private lateinit var binding: FragmentNapCofirmBinding
    private val viewModel: NapViewModel by viewModels()
    private var user = FirebaseAuth.getInstance().currentUser
    private lateinit var dialogA: ShowDialog.Builder
    private lateinit var loadingA: LoadingDialog
    private val userDatabase =
        FirebaseDatabase.getInstance().getReference("user").child(user!!.uid)
    private val incomeDatabase =
        FirebaseDatabase.getInstance().getReference("income").child(user!!.uid)
    private val rutNapDatabase =
        FirebaseDatabase.getInstance().getReference("rutnap").child(getUID() + "nap")
    private var pkg = 0
    private var dataFullUser: User? = null
    private var userPackage: Int? = 0


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentNapCofirmBinding.inflate(inflater, container, false)
        arguments?.let { bundle ->
            if (bundle != null) {
                userPackage = bundle.getInt("userPackage")!!
                Log.d("kienda", "onCreateView: $userPackage")
            }
        }
        init()

        //Check n???p
        rutNapDatabase.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                //C?? th??? r??t v?? n???p n???u status true
                checkForSetDataToUserFragment()
                setInfoAdmin()
                setOnClickForViews()
//                if (snapshot.child("status").getValue(Boolean::class.java) == true) {
//
//                } else {
//                    dialogA.show(
//                        "Th??ng b??o",
//                        "B???n ???? c?? y??u c???u n???p, vui l??ng ?????i x??? l?? tr?????c khi ti???p t???c th???c hi???n giao d???ch m???i!"
//                    )
//                    requireActivity().onBackPressed()
//                }
            }

            override fun onCancelled(error: DatabaseError) {
            }

        })



        return binding.root;
    }

    private fun setInfoAdmin() {
        FirebaseDatabase.getInstance().getReference("admin")
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val name = snapshot.child("name").getValue(String::class.java).toString()
                    val bank = snapshot.child("name").getValue(String::class.java).toString()
                    val stk = snapshot.child("stk").getValue(String::class.java).toString()

                    val user = User()
                    user.name = name
                    user.bank = bank
                    user.stk = stk
                    viewModel.user.set(user)
                }

                override fun onCancelled(error: DatabaseError) {
                }

            })
    }

    private fun updateTheTempDateInFirebase() {
        var userNameHashMap: HashMap<String, String> = HashMap<String, String>()
        userNameHashMap["tempDate"] = System.currentTimeMillis().toString()
        userDatabase.updateChildren(userNameHashMap as Map<String, Any>).addOnSuccessListener {

        }.addOnFailureListener {
            Log.d("kienda", "updateTheUserPackage: + ${it.message}")
        }
    }

    private fun addToInComeDatabase(value: String, userName: String, userMoney: String) {
//        val inCome =
//            History(userName, userMoney, convertTime(System.currentTimeMillis()), "Nap", "False")
//        incomeDatabase.child(value).setValue(inCome)
//        rutNapDatabase.setValue(inCome)
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
            Log.d("kienda", "updateTheUserPackage: + ${it.message}")
        }
    }


    private fun checkForAddToHistory(userMoney: String) {
        if (user != null) {
            userDatabase.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val user = snapshot.getValue(User::class.java)
                    val currentIndex = user?.index!!.toInt() + 1
                    addToInComeDatabase(user.index, user.name, userMoney)
                    updateTheIndexOfTheUser(currentIndex.toString())
                }

                override fun onCancelled(error: DatabaseError) {
                }

            })
        }
    }


    private fun checkForSetDataToUserFragment() {
        if (user != null) {
            userDatabase.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val user = snapshot.getValue(User::class.java)
                    dataFullUser = user
//                    viewModel.user.set(user)
                    Log.d("testkakkaka", "user: $user")
                    binding.body = "NAP TIEN GOI $userPackage"
                }

                override fun onCancelled(error: DatabaseError) {
                }

            })
        }
    }

    private fun setOnClickForViews() {
        binding.btnConfirmNapTien.setOnClickListener {
            showDialogToConfirm()
        }
        binding.btnCancelNapTien.setOnClickListener {
            moveToMainActivity()
        }
    }

    private fun showDialogToConfirm() {
        var dialog: Dialog? = null
        dialog = ShowDialog.Builder(requireContext())
            .title("B???n c?? ch???c ch???n l?? ???? chuy???n kho???n cho ng?????i nh???n kh??ng?")
            .message("")
            .setRightButton("B??? qua", object : DialogRightInterface {
                override fun onClick() {
                    dialog?.dismiss()
                }
            })
            .setLeftButton("Ch???c ch???n", object : DialogLeftInterface {
                override fun onClick() {
                    checkForUserInFormation()
                    updateDataForUser()
                    updateTheTempDateInFirebase()
                    dialog?.dismiss()
                }
            })
            .confirm()
        dialog?.show()
    }

    private fun updateDataForUser() {
        var userNameHashMap: HashMap<String, String> = HashMap<String, String>()
        userNameHashMap["timeAvailableForUserPackage"] = getListMoney()[userPackage!! - 1].tgian
        userDatabase.updateChildren(userNameHashMap as Map<String, Any>).addOnSuccessListener {
        }.addOnFailureListener {
            Log.d("kienda", "updateTheUserPackage: + ${it.message}")
        }
    }

    //CHuy???n tr???ng th??i ch??? x??c nh???n, cho  th???ng mainActivity gi???i quy???t
    private fun checkForUserInFormation() {
        val naprut = NapRut(
            getUID(),
            getListMoney()[userPackage!! - 1].gia,
            pkg.toString(),
            System.currentTimeMillis().toString(),
            false,
            false
        )
        //?????y d??? li???u v?? ch??? tr???ng th??i r??t

        //B???n noti telegram
        rutNapDatabase.setValue(naprut).addOnCompleteListener {
            val body = "===Y??U C???U N???P TI???N==     " +
                    "T??n: ${dataFullUser?.name?.toUpperCase()}" +
                    "\n STK: ${dataFullUser?.stk}" +
                    "\n Ng??n h??ng: ${dataFullUser?.bank}" +
                    "\n S??? ti???n: ${getListMoney()[userPackage!! - 1].gia}" +
                    "\n G??i: $userPackage"
            GlobalScope.launch {
                SendTelegram.send(body)
                requireActivity().runOnUiThread {
                    dialogA.show("Giao d???ch th??nh c??ng", "Vui l??ng ch??? Admin x??c nh???n")
                    moveToMainActivity()
                }
            }
        }
    }


    private fun moveToMainActivity() {
        findNavController().navigate(R.id.homeFragment)
    }

    fun init() {
        binding.setVariable(BR.viewModel, viewModel)
        loadingA = LoadingDialog.getInstance(requireContext())
        dialogA = ShowDialog.Builder(requireContext())
//        pkg = this.arguments?.getInt("package") ?: 1
        Log.d("confirmPKG", "pkg: $pkg")

        binding.apply {
            packagex = userPackage.toString()
            money = getListMoney()[userPackage!! - 1].gia
        }

        requireActivity().findViewById<BottomNavigationView>(R.id.bottom_nav).visibility =
            View.VISIBLE
        viewModel.load()
    }

    override fun onClick(v: View?) {
        when (v?.id) {

        }
    }


}


