package com.subi.likeanh.user

import android.os.Bundle
import android.telephony.PhoneNumberUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.subi.likeanh.BR
import com.subi.likeanh.R
import com.subi.likeanh.databinding.FragmentEditUserBinding
import com.subi.likeanh.databinding.FragmentUserBinding
import com.subi.likeanh.model.User
import com.subi.likeanh.utils.LoadingDialog
import com.subi.nails2022.view.ShowDialog
import com.google.android.gms.tasks.OnFailureListener




class EditUserFragment : Fragment(), View.OnClickListener {
    private lateinit var binding: FragmentEditUserBinding
    private val viewModel: UserViewModel by viewModels()
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
        binding = FragmentEditUserBinding.inflate(inflater, container, false)
        init()
        checkForSetDataToUserFragment()
        setOnClickForViews()
        return binding.root;
    }

    private fun setOnClickForViews() {
        binding.tvUpdateAccount.setOnClickListener(this)
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

    fun init() {
        binding.setVariable(BR.viewModel, viewModel)
        requireActivity().findViewById<BottomNavigationView>(R.id.bottom_nav).visibility =
            View.VISIBLE
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.tvUpdateAccount -> {
                checkForUserInFormation()
            }
        }
    }


    private fun checkForUserInFormation() {
        val userName = binding.edtUserName.text.toString()
        val userAccount: String = binding.edtUserAccount.text.toString()
        val bank: String = binding.edtUserBank.text.toString()
        val userPhone = binding.edtUserPhoneNumber.text.toString()
        val userIntroducePhone = binding.edtIntroducePhoneNumber.text.toString()


        if (userName.isNotEmpty() && userAccount.isNotEmpty() && bank.isNotEmpty() && userPhone.isNotEmpty() && userIntroducePhone
                .isNotEmpty()
        ) {

            var userNameHashMap: HashMap<String, String> = HashMap<String, String>()
            userNameHashMap["bank"] = bank
            userNameHashMap["name"] = userName
            userNameHashMap["phone"] = userPhone
            userNameHashMap["sdtGt"] = userIntroducePhone
            userNameHashMap["userBank"] = userAccount
            ref.updateChildren(userNameHashMap as Map<String, Any>).addOnSuccessListener {
                findNavController().navigate(R.id.action_editUserFragment_to_userFragment)
            }

        } else {
            Toast.makeText(activity, "không được để trông thông tin", Toast.LENGTH_SHORT).show()
        }
    }

    private companion object {
        private const val TAG = "Kienda"
    }
}