package com.subi.likeanh

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.provider.Settings
import android.telephony.PhoneNumberUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.subi.likeanh.databinding.FragmentRegBinding
import com.subi.likeanh.model.User
import com.subi.nails2022.view.ShowDialog
import com.subi.likeanh.utils.LoadingDialog
import com.subi.likeanh.utils.Utils

class RegisterFragment : Fragment() {
    private lateinit var binding: FragmentRegBinding
    private var auth = FirebaseAuth.getInstance()
    private var database = FirebaseDatabase.getInstance().getReference("user")
    private lateinit var loading: LoadingDialog
    private lateinit var dialog: ShowDialog.Builder

    @SuppressLint("HardwareIds")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentRegBinding.inflate(inflater, container, false)
        init()
        binding.apply {
            //radom 4 số
            var rd = (1000..9999).random()
            btnRd.setOnClickListener {
                rd = (1000..9999).random()
                tvRd.text = rd.toString()
            }
            val androidID =
                Settings.Secure.getString(activity?.contentResolver, Settings.Secure.ANDROID_ID)
                    .toString()
            val key = "$androidID"
            tvRd.text = rd.toString()
            btnDangKy.setOnClickListener {
                loading.show()
                val hotenx = edtHoTen.text.toString()
                val sdtx = edtPhone.text.toString()
                val pass1 = edtMkDK.text.toString()
                val pass2 = edtMkDK2.text.toString()

                if (hotenx.isNotEmpty() && sdtx.isNotEmpty() && pass1.isNotEmpty() && pass2.isNotEmpty() && edtCaptcha.text.toString()
                        .isNotEmpty()
                ) {
                    if (hotenx.length < 6) {
                        loading.dismiss()
                        dialog.show("Thông báo", "Vui lòng nhập đầy đủ họ tên!")
                    } else if (!PhoneNumberUtils.isGlobalPhoneNumber(sdtx)) {
                        dialog.show("Thông báo", "Số điện thoại không chính xác!")
                        loading.dismiss()

                    } else if (pass1 != pass2) {
                        loading.dismiss()
                        dialog.show("Thông báo", "Mật khẩu không khớp nhau!")
                    } else {

                        if (edtCaptcha.text.toString().toInt() != rd) {
                            loading.dismiss()
                            dialog.show("Thất bại!", "Mã xác nhập không chính xác")
                        } else {
                            database.orderByChild("idPhone")
                                .equalTo(androidID)
                                .addListenerForSingleValueEvent(object :
                                    ValueEventListener {
                                    override fun onDataChange(snapshot: DataSnapshot) {
                                        if (snapshot.exists()) {
                                            loading.dismiss()
                                            dialog.show(
                                                "Thất bại!",
                                                "Điện thoại này đã đăng ký một tài khoản trước đó!"
                                            )
                                        } else {
                                            auth.createUserWithEmailAndPassword(
                                                "$sdtx@gmail.com",
                                                pass1
                                            )
                                                .addOnCompleteListener {
                                                    if (it.isSuccessful) {
                                                        val uid = it.result?.user?.uid.toString()
                                                        val user = User(
                                                            uid,
                                                            sdtx,
                                                            hotenx,
                                                            "",
                                                            true,
                                                            "",
                                                            "0",
                                                            edtMagt.text.toString(),
                                                            androidID,
                                                            "0",
                                                            "0",
                                                            "0",
                                                            "0",
                                                            "0",
                                                            "true",
                                                            "0",
                                                            "0",
                                                            "0"
                                                        )
                                                        database.child(uid).setValue(user)
                                                            .addOnCompleteListener {
                                                                dialog.show(
                                                                    "Chúc mừng!",
                                                                    "Đăng ký thành công!"
                                                                )
                                                             val rn =   FirebaseDatabase.getInstance().getReference("rutnap")
                                                                rn.child(Utils.getUID() + "nap").child("status").setValue(true)
                                                                rn.child(Utils.getUID() + "rut").child("status").setValue(true)
                                                                findNavController().navigate(R.id.action_registerFragment_to_homeFragment)
                                                                loading.dismiss()
                                                            }

                                                    } else {
                                                        dialog.show(
                                                            "Thất bại!",
                                                            "Tài khoản đã tồn tại!"
                                                        )
                                                        loading.dismiss()
                                                    }
                                                }
                                        }
                                    }

                                    override fun onCancelled(error: DatabaseError) {

                                    }
                                })
                        }
                    }
                } else {
                    dialog.show("Thông báo", "Không được để trống!")
                    loading.dismiss()
                }
            }
            btnNhapLai.setOnClickListener {
                (requireActivity() as Activity).onBackPressed()
            }
        }
        return binding.root
    }

    fun init() {
        loading = LoadingDialog.getInstance(requireContext())
        dialog = ShowDialog.Builder(requireContext())
        requireActivity().findViewById<BottomNavigationView>(R.id.bottom_nav).visibility = View.GONE
    }
}