package com.subi.likeanh

import android.os.Bundle
import android.provider.Settings
import android.telephony.PhoneNumberUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.subi.likeanh.databinding.FragmentLoginBinding
import com.subi.nails2022.view.ShowDialog
import com.subi.likeanh.utils.LoadingDialog

class LoginFragment : Fragment() {
    lateinit var binding: FragmentLoginBinding
    lateinit var dialog: ShowDialog.Builder
    lateinit var loading: LoadingDialog
    private var auth = FirebaseAuth.getInstance()
    var database = FirebaseDatabase.getInstance().getReference("user")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        init()
        val androidID =
            Settings.Secure.getString(activity?.contentResolver, Settings.Secure.ANDROID_ID)
                .toString()
        binding.apply {
            btnDangNhap.setOnClickListener {
                loading.show()
                val sdtx = edtTk.text.toString()
                val pass1 = edtMk.text.toString()
                if (sdtx.isNotEmpty() && pass1.isNotEmpty()) {
                    if (!PhoneNumberUtils.isGlobalPhoneNumber(sdtx)) {
                        dialog.show("Thông báo", "Số điện thoại không chính xác!")
                        loading.dismiss()
                    } else {
                        auth.signInWithEmailAndPassword("$sdtx@gmail.com", pass1)
                            .addOnCompleteListener {
                                if (it.isSuccessful) {

                                    database.child(FirebaseAuth.getInstance().currentUser?.uid.toString()).addListenerForSingleValueEvent(object : ValueEventListener{
                                        override fun onDataChange(snapshot: DataSnapshot) {
                                            val keyID  = snapshot.child("idPhone").getValue(String::class.java)
                                            if (keyID==""){
                                                database.child(FirebaseAuth.getInstance().currentUser?.uid.toString()).child("idPhone").setValue(androidID).addOnCompleteListener {
                                                    dialog.show("Chúc mừng!",
                                                        "Đăng nhập thành công!")
                                                    findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
                                                    loading.dismiss()
                                                }
                                            }
                                            else if (keyID.equals(androidID)){
                                                dialog.show("Chúc mừng!",
                                                    "Đăng nhập thành công!")
                                                findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
                                                loading.dismiss()
                                            }
                                            else{
                                                FirebaseAuth.getInstance().signOut()
                                                loading.dismiss()
                                                dialog.show("Thất bại!",
                                                    "Tài khoản chỉ được đăng nhập ở 1 máy duy nhất!")
                                            }
                                        }

                                        override fun onCancelled(error: DatabaseError) {
                                        }

                                    })
                                } else {
                                    dialog.show("Thông báo", "Sai tài khoản hoặc mật khẩu!")
                                    loading.dismiss()
                                }
                            }
                    }
                } else {
                    dialog.show("Thông báo", "Không được để trống!")
                    loading.dismiss()
                }
            }

            tvDangKy.setOnClickListener {
                findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
            }
        }

        return binding.root;
    }

    fun init() {
        if (FirebaseAuth.getInstance().currentUser != null) {
            findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
        }
        loading = LoadingDialog.getInstance(requireContext())
        dialog = ShowDialog.Builder(requireContext())
        requireActivity().findViewById<BottomNavigationView>(R.id.bottom_nav).visibility = View.GONE
    }
}