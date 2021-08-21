package com.subi.likeanh.money.nap

import android.app.Dialog
import android.os.Bundle
import android.os.Handler
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
    var pkg = 0
    var dataFullUser: User? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentNapCofirmBinding.inflate(inflater, container, false)

        init()
        checkForSetDataToUserFragment()
        setOnClickForViews()

        return binding.root;
    }

    private fun addToInComeDatabase(value: String, userName: String, userMoney: String) {
        val inCome =
            History(userName, userMoney, convertTime(System.currentTimeMillis()), "Nap", "False")
        incomeDatabase.child(value).setValue(inCome)
        rutNapDatabase.setValue(inCome)
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
                    viewModel.user.set(user)
                    Log.d("testkakkaka", "user: $user")
                    binding.body = "NAP TIEN GOI $pkg"
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
            .title("Bạn có chắc chắn là đã chuyển khoản cho người nhận không?")
            .message("")
            .setRightButton("Bỏ qua", object : DialogRightInterface {
                override fun onClick() {
                    dialog?.dismiss()
                }
            })
            .setLeftButton("Chắc chắn", object : DialogLeftInterface {
                override fun onClick() {
                    checkForUserInFormation()
                    dialog?.dismiss()
                }
            })
            .confirm()
        dialog?.show()
    }

    private fun checkForGetTotalMoney() {

    }

    //CHuyển trạng thái chờ xác nhận, cho  thằng mainActivity giải quyết
    private fun checkForUserInFormation() {
        val naprut = NapRut(getUID(),
            getListMoney()[pkg - 1].gia,
            pkg.toString(),
            System.currentTimeMillis().toString(),
            false,
            false)
        //Đẩy dữ liệu và chờ trạng thái rút

        //Bắn noti telegram
        rutNapDatabase.setValue(naprut).addOnCompleteListener {
            val body = "" +
                    "Tên: ${dataFullUser?.name?.toUpperCase()}" +
                    "\n STK: ${dataFullUser?.stk}" +
                    "\n Ngân hàng: ${dataFullUser?.bank}" +
                    "\n Số tiền: ${getListMoney()[pkg - 1].gia}" +
                    "\n Gói: $pkg"
            GlobalScope.launch {
                if (SendTelegram.send(body)) {
                }
                ialogA.show("Giao dịch thành công", "Vui lòng chờ Admin xác nhận")
                moveToMainActivity()
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
        pkg = this.arguments?.getInt("package") ?: 1
        Log.d("confirmPKG", "pkg: $pkg")

        binding.apply {
            packagex = pkg.toString()
            money = getListMoney()[pkg - 1].gia
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