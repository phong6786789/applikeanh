package com.subi.likeanh.money.nap

import android.R
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
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
import com.subi.likeanh.databinding.FragmentNapBinding
import com.subi.likeanh.databinding.FragmentNapCofirmBinding
import com.subi.likeanh.model.User
import com.subi.likeanh.utils.LoadingDialog
import com.subi.nails2022.view.DialogLeftInterface
import com.subi.nails2022.view.DialogRightInterface
import com.subi.nails2022.view.ShowDialog

class NapConfirmFragment : Fragment(), View.OnClickListener {
    private lateinit var binding: FragmentNapCofirmBinding
    private val viewModel: NapViewModel by viewModels()
    private var ref = FirebaseDatabase.getInstance().getReference("money")
    private var user = FirebaseAuth.getInstance().currentUser
    private lateinit var loading: LoadingDialog
    private lateinit var dialog: ShowDialog.Builder

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
            val ref =
                FirebaseDatabase.getInstance().getReference("user").child(user!!.uid)
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
        var dialog: Dialog?=null
        dialog= ShowDialog.Builder(requireContext())
            .title("Bạn đã xác nhận chuyển khoản")
            .message("")
            .setRightButton("Bỏ qua", object : DialogRightInterface {
                override fun onClick() {
                    dialog?.dismiss()
                }
            })
            .setLeftButton("Xác nhận", object : DialogLeftInterface {
                override fun onClick() {
                    Toast.makeText(activity, "Đã chuyển khoản thành công", Toast.LENGTH_SHORT)
                        .show()
                    findNavController().navigate(com.subi.likeanh.R.id.action_napCofirmFragment_to_moneyFragment)
                    dialog?.dismiss()
                }
            })
            .confirm()
        dialog?.show()
    }

    private fun moveToMainActivity() {
        findNavController().navigate(com.subi.likeanh.R.id.action_napCofirmFragment_to_moneyFragment)
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