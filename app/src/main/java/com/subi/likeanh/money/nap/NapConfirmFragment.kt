package com.subi.likeanh.money.nap

import android.app.Dialog
import android.os.Bundle
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
import com.subi.likeanh.databinding.FragmentNapCofirmBinding
import com.subi.likeanh.model.User
import com.subi.likeanh.utils.LoadingDialog
import com.subi.nails2022.view.DialogLeftInterface
import com.subi.nails2022.view.DialogRightInterface
import com.subi.nails2022.view.ShowDialog

class NapConfirmFragment : Fragment(), View.OnClickListener {
    private lateinit var binding: FragmentNapCofirmBinding
    private val viewModel: NapViewModel by viewModels()
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
        binding = FragmentNapCofirmBinding.inflate(inflater, container, false)
        init()
        checkForSetDataToUserFragment()
        setOnClickForViews()
        return binding.root;
    }

    private fun checkForSetDataToUserFragment() {
        if (user != null) {
            ref.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val user = snapshot.getValue(User::class.java)
                    viewModel.user.set(user)

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
            .title("Bạn đã xác nhận chuyển khoản")
            .message("")
            .setRightButton("Bỏ qua", object : DialogRightInterface {
                override fun onClick() {
                    dialog?.dismiss()
                }
            })
            .setLeftButton("Xác nhận", object : DialogLeftInterface {
                override fun onClick() {
                    checkForUserInFormation()
                    dialog?.dismiss()
                    moveToMainActivity()
                }
            })
            .confirm()
        dialog?.show()
    }

    private fun checkForUserInFormation() {
        val userMoney = binding.edtTotalMoney.text.toString()
        if (userMoney.isNotEmpty()
        ) {

            var userNameHashMap: HashMap<String, String> = HashMap<String, String>()
            userNameHashMap["transferTime"] = System.currentTimeMillis().toString()
            userNameHashMap["totalMoney"] = userMoney
            ref.updateChildren(userNameHashMap as Map<String, Any>).addOnSuccessListener {
                Toast.makeText(
                    activity,
                    "Cập nhật thông tin cá nhân thành công",
                    Toast.LENGTH_SHORT
                ).show()

            }
        }
    }


    private fun moveToMainActivity() {
        findNavController().navigate(com.subi.likeanh.R.id.action_napCofirmFragment_to_napFragment2)
    }

    fun init() {
        binding.setVariable(BR.viewModel, viewModel)
        loading = LoadingDialog.getInstance(requireContext())
        dialog = ShowDialog.Builder(requireContext())
        requireActivity().findViewById<BottomNavigationView>(com.subi.likeanh.R.id.bottom_nav).visibility =
            View.VISIBLE
        viewModel.load()
    }

    override fun onClick(v: View?) {
        when (v?.id) {

        }
    }


}