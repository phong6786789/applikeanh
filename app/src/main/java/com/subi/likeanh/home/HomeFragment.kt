package com.subi.likeanh.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.subi.likeanh.BR
import com.subi.likeanh.R
import com.subi.likeanh.adapter.HomeAdapter
import com.subi.likeanh.callback.OnItemClick
import com.subi.likeanh.databinding.FragmentHomeBinding
import com.subi.likeanh.model.*
import com.subi.likeanh.utils.LoadingDialog
import com.subi.nails2022.view.ShowDialog
import java.text.Format
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashMap


class HomeFragment : Fragment(), OnItemClick {
    private lateinit var binding: FragmentHomeBinding
    private val viewModel: HomeViewModel by viewModels()
    private var list = arrayListOf<Product>()
    private var user = FirebaseAuth.getInstance().currentUser
    private val userDatabase =
        FirebaseDatabase.getInstance().getReference("user").child(user!!.uid)

    private val likeDatabaseError =
        FirebaseDatabase.getInstance().getReference("lịke").child(user!!.uid)

    private lateinit var loading: LoadingDialog
    private lateinit var dialog: ShowDialog.Builder

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        init()
        binding.apply {
            rcv.apply {
                adapter = HomeAdapter(getDataForRecyclerView(), this@HomeFragment)
                layoutManager = GridLayoutManager(requireActivity(), 2)
                hasFixedSize()
            }
        }
        checkToUpdateTheIsLikeInFirebase()
        return binding.root;
    }

    private fun addToInComeDatabase(value: String, userName: String, userMoney: String) {
        val income =
            Income(userName, userMoney, convertTimeForInCome(System.currentTimeMillis()), "0")
        likeDatabaseError.child(value).setValue(income)
    }

    private fun convertTimeForInCome(time: Long): String {
        val date = Date(time)
        val format: Format = SimpleDateFormat("dd-M-yyyy hh:mm:ss")
        return format.format(date)
    }


    private fun updateTheUserPackage(valueA: String, valueB: String, valueC: String) {
        var userNameHashMap: HashMap<String, String> = HashMap<String, String>()
        userNameHashMap["numberLikes"] = valueA
        userNameHashMap["totalLike"] = valueB
        userNameHashMap["totalMoney"] = valueC
        userDatabase.updateChildren(userNameHashMap as Map<String, Any>).addOnSuccessListener {
        }.addOnFailureListener {
            Log.d("kienda", "updateTheUserPackage: + ${it.message}")
        }
    }

    private fun updateTheIsLikeInFirebase(value: String) {
        var userNameHashMap: HashMap<String, String> = HashMap<String, String>()
        userNameHashMap["isAvailableToLike"] = value
        userDatabase.updateChildren(userNameHashMap as Map<String, Any>).addOnSuccessListener {

        }.addOnFailureListener {
            Log.d("kienda", "updateTheUserPackage: + ${it.message}")
        }
    }

    private fun checkForSetDataToUserFragment() {
        Log.d(TAG, "checkForSetDataToUserFragment: ")
        if (user != null) {
            val ref =
                FirebaseDatabase.getInstance().getReference("user").child(user!!.uid)
            ref.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    Log.d(TAG, "onDataChange: ")
                    val user = snapshot.getValue(User::class.java)
                    checkToUpdateTheLike(user!!)
                }

                override fun onCancelled(error: DatabaseError) {
                }
            })
        }
    }

    private fun updateTheCurrentInFirebase() {
        var userNameHashMap: HashMap<String, String> = HashMap<String, String>()
        userNameHashMap["isAvailableToLike"] = "true"
        userNameHashMap["numberLikes"] = "0"
        userDatabase.updateChildren(userNameHashMap as Map<String, Any>).addOnSuccessListener {

        }.addOnFailureListener {
            Log.d("kienda", "updateTheUserPackage: + ${it.message}")
        }
    }

    private fun checkToUpdateTheIsLikeInFirebase() {
        Log.d(TAG, "checkToUpdateTheIsLikeInFirebase: ")
        if (user != null) {
            userDatabase.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val user = snapshot.getValue(User::class.java)
                    if (user?.currentDate != convertTimeDay(System.currentTimeMillis())) {
                        checkToUpdateCurrentDateInFirebase(user!!)
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.d(TAG, "onDataChange: B")
                }
            })
        }
    }

    private fun checkUpdateDate(availableTime: Long, expireDate: Long) {
        if (availableTime <= expireDate) {
            updateTheCurrentInFirebase()
            return
        }
        updateUserPackageTo0()
        dialog.show(
            "Gói của bạn đã hết hạn",
            "Bạn cần nạp tiền để thêm gói mới để có thể tiếp tục like"
        )
    }


    private fun updateUserPackageTo0() {
        var userNameHashMap: HashMap<String, String> = HashMap<String, String>()
        userNameHashMap["userPackage"] = "0"
        userDatabase.updateChildren(userNameHashMap as Map<String, Any>).addOnSuccessListener {
        }.addOnFailureListener {

        }
    }


    private fun checkToUpdateCurrentDateInFirebase(user: User) {
        when (user.userPackage.toInt()) {
            0 -> {

            }
            1 -> {
                val timeExpireDate = user.tempDate.toLong() + 3 * 86400
                checkUpdateDate(user.tempDate.toLong(), timeExpireDate)
            }
            2 -> {
                val timeExpireDate = user.tempDate.toLong() + 7 * 86400
                checkUpdateDate(user.tempDate.toLong(), timeExpireDate)
            }
            3 -> {
                val timeExpireDate = user.tempDate.toLong() + 20 * 86400
                checkUpdateDate(user.tempDate.toLong(), timeExpireDate)
            }
            4 -> {
                val timeExpireDate = user.tempDate.toLong() + 30 * 86400
                checkUpdateDate(user.tempDate.toLong(), timeExpireDate)
            }
            5 -> {
                val timeExpireDate = user.tempDate.toLong() + 40 * 86400
                checkUpdateDate(user.tempDate.toLong(), timeExpireDate)
            }
            6 -> {
                val timeExpireDate = user.tempDate.toLong() + 50 * 86400
                checkUpdateDate(user.tempDate.toLong(), timeExpireDate)
            }
            7 -> {
                val timeExpireDate = user.tempDate.toLong() + 68 * 86400
                checkUpdateDate(user.tempDate.toLong(), timeExpireDate)
            }
        }
    }

    private fun convertTimeDay(time: Long): String {
        val date = Date(time)
        val format: Format = SimpleDateFormat("dd")
        return format.format(date)
    }


    private fun convertTimeMonth(time: Long): String {
        val date = Date(time)
        val format: Format = SimpleDateFormat("MM")
        return format.format(date)
    }


    private fun checkToUpdateTheLike(user: User) {
        Log.d(TAG, "checkToUpdateTheLike: ")
        when (user.userPackage.toInt()) {
            0 -> {
                dialog.show("Thất bại", "Bạn cần đăng kí 1 gói nào đó để like ảnh")
            }
            1 -> {
                if (user.isAvailableToLike == "true") {
                    if (user.numberLikes.toInt() >= 30) {
                        dialog.show("Bạn đã dùng hết số lượt like ngày hôm nay", "")
                        updateTheIsLikeInFirebase("false")
                        return
                    }
                    val valueA = user.numberLikes.toInt() + 1
                    val valueB = user.totalLike.toInt() + 1
                    val valueC = user.totalMoney.toLong() + 400
                    updateTheUserPackage(valueA.toString(), valueB.toString(), valueC.toString())
                    addToInComeDatabase(valueB.toString(), user.name, getListMoney()[0].gia_like)
                    return
                }
                dialog.show("Bạn đã dùng hết số lượt like ngày hôm nay", "")
            }
            2 -> {
                if (user.isAvailableToLike == "true") {
                    if (user.numberLikes.toInt() >= 30) {
                        dialog.show("Bạn đã dùng hết số lượt like ngày hôm nay", "")
                        updateTheIsLikeInFirebase("false")
                        return
                    }
                    val valueA = user.numberLikes.toInt() + 1
                    val valueB = user.totalLike.toInt() + 1
                    val valueC = user.totalMoney.toLong() + 19200
                    updateTheUserPackage(valueA.toString(), valueB.toString(), valueC.toString())
                    addToInComeDatabase(valueB.toString(), user.name, getListMoney()[1].gia_like)
                    return
                }
                dialog.show("Bạn đã dùng hết số lượt like ngày hôm nay", "")
            }
            3 -> {
                if (user.isAvailableToLike == "true") {
                    if (user.numberLikes.toInt() >= 30) {
                        dialog.show("Bạn đã dùng hết số lượt like ngày hôm nay", "")
                        updateTheIsLikeInFirebase("false")
                        return
                    }
                    val valueA = user.numberLikes.toInt() + 1
                    val valueB = user.totalLike.toInt() + 1
                    val valueC = user.totalMoney.toLong() + 25000
                    updateTheUserPackage(valueA.toString(), valueB.toString(), valueC.toString())
                    addToInComeDatabase(valueB.toString(), user.name, getListMoney()[2].gia_like)
                    return
                }
                dialog.show("Bạn đã dùng hết số lượt like ngày hôm nay", "")
            }
            4 -> {
                if (user.isAvailableToLike == "true") {
                    if (user.numberLikes.toInt() >= 35) {
                        dialog.show("Bạn đã dùng hết số lượt like ngày hôm nay", "")
                        updateTheIsLikeInFirebase("false")
                        return
                    }
                    val valueA = user.numberLikes.toInt() + 1
                    val valueB = user.totalLike.toInt() + 1
                    val valueC = user.totalMoney.toLong() + 495000
                    updateTheUserPackage(valueA.toString(), valueB.toString(), valueC.toString())
                    addToInComeDatabase(valueB.toString(), user.name, getListMoney()[3].gia_like)
                    return
                }
                dialog.show("Bạn đã dùng hết số lượt like ngày hôm nay", "")
            }
            5 -> {
                if (user.isAvailableToLike == "true") {
                    if (user.numberLikes.toInt() >= 35) {
                        dialog.show("Bạn đã dùng hết số lượt like ngày hôm nay", "")
                        updateTheIsLikeInFirebase("false")
                        return
                    }
                    val valueA = user.numberLikes.toInt() + 1
                    val valueB = user.totalLike.toInt() + 1
                    val valueC = user.totalMoney.toLong() + 75000
                    updateTheUserPackage(valueA.toString(), valueB.toString(), valueC.toString())
                    addToInComeDatabase(valueB.toString(), user.name, getListMoney()[4].gia_like)
                    return
                }
                dialog.show("Bạn đã dùng hết số lượt like ngày hôm nay", "")
            }
            6 -> {
                if (user.isAvailableToLike == "true") {
                    if (user.numberLikes.toInt() >= 40) {
                        dialog.show("Bạn đã dùng hết số lượt like ngày hôm nay", "")
                        updateTheIsLikeInFirebase("false")
                        return
                    }
                    val valueA = user.numberLikes.toInt() + 1
                    val valueB = user.totalLike.toInt() + 1
                    val valueC = user.totalMoney.toLong() + 162500
                    updateTheUserPackage(valueA.toString(), valueB.toString(), valueC.toString())
                    addToInComeDatabase(valueB.toString(), user.name, getListMoney()[5].gia_like)
                    return
                }
                dialog.show("Bạn đã dùng hết số lượt like ngày hôm nay", "")
            }
            7 -> {
                if (user.isAvailableToLike == "true") {
                    if (user.numberLikes.toInt() >= 40) {
                        dialog.show("Bạn đã dùng hết số lượt like ngày hôm nay", "")
                        updateTheIsLikeInFirebase("false")
                        return
                    }
                    val valueA = user.numberLikes.toInt() + 1
                    val valueB = user.totalLike.toInt() + 1
                    val valueC = user.totalMoney.toLong() + 205882
                    updateTheUserPackage(valueA.toString(), valueB.toString(), valueC.toString())
                    addToInComeDatabase(valueB.toString(), user.name, getListMoney()[6].gia_like)
                    return
                }
                dialog.show("Bạn đã dùng hết số lượt like ngày hôm nay", "")
            }
        }
    }

    fun init() {
        loading = LoadingDialog.getInstance(requireContext())
        dialog = ShowDialog.Builder(requireContext())
        binding.setVariable(BR.viewModel, viewModel)
        requireActivity().findViewById<BottomNavigationView>(R.id.bottom_nav).visibility =
            View.VISIBLE
        val database = FirebaseDatabase.getInstance().getReference("user")
        database.child(FirebaseAuth.getInstance().currentUser?.uid.toString())
            .addValueEventListener(object :
                ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    //Check block account
                    if (snapshot.child("status")
                            .getValue(Boolean::class.java) == false
                    ) {
                        FirebaseAuth.getInstance().signOut()
                        requireActivity().onBackPressed()
                        dialog.show(
                            "Lỗi",
                            "Tài khoản của bạn đã bị khoá, vui lòng liên hệ admin!"
                        )
                        return
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                }
            })
    }

    private fun getDataForRecyclerView(): List<Product> {
        val list = arrayListOf<Product>()
        list.add(Product("Laptop dell g5 5500a", R.drawable.ic_laptop_dell_gaming_g5_5500a, false))
        list.add(Product("Laptop dell vostro 5502", R.drawable.ic_laptop_dell_vostro_5502, false))
        list.add(Product("Laptop dell vps 9310", R.drawable.ic_laptop_dell_xps_9310, false))
        list.add(Product("Laptop dell vostro 3400", R.drawable.ic_laptop_dell_vostro_3400, false))
        list.add(Product("Laptop dell vostro 3400", R.drawable.ic_laptop_dell_vostro_3400, false))
        list.add(Product("Laptop dell vostros 3405", R.drawable.ic_laptop_dell_vostro_3405, false))
        list.add(Product("Monitor 24 HP 220 ", R.drawable.ic_monitor_hp_v220, false))
        list.add(Product("Monitor 27X", R.drawable.ic_monitor_hp_m27x, false))
        list.add(Product("Monitor HP 27h", R.drawable.ic_monitor_hp_m27h, false))
        list.add(Product("Monitor HP 24v", R.drawable.ic_monitor_hp_m24v, false))
        list.add(Product("Monitor HP 27iomen", R.drawable.ic_monitor_hp_27iomen, false))
        list.add(Product("Monitor HP 24fw", R.drawable.ic_monitor_hp_m24fw, false))
        list.add(Product("Monitor HP p21v", R.drawable.ic_monitor_hp_p21v, false))
        list.add(Product("Monitor HP p24", R.drawable.ic_monitor_hp_p204ov, false))
        list.add(Product("Monitor HP m24f", R.drawable.ic_monitor_hp_m24f, false))
        list.add(Product("Monitor HP 27xq", R.drawable.ic_monitor_hp_27xq, false))
        list.add(Product("Set A", R.drawable.ic_set_a, false))
        list.add(Product("Set B", R.drawable.ic_set_b, false))
        list.add(Product("Set C", R.drawable.ic_set_c, false))
        list.add(Product("Set DA", R.drawable.ic_set_d, false))
        list.add(Product("Set DB", R.drawable.ic_set_d, false))
        list.add(Product("Set G", R.drawable.ic_set_g, false))
        list.add(Product("Nai Chuoi", R.drawable.ic_nai_chuoi, false))
        list.add(Product("Qua Chuoi", R.drawable.ic_qua_chuoi, false))
        list.add(Product("Nuoc rua bat", R.drawable.nuoc_rua_bat_sun_line_can_to, true))
        list.add(Product("Qua Tao", R.drawable.ic_ba_qua_tao, false))
        list.add(Product("Chum Nen", R.drawable.ic_chum_nen, false))
        list.add(Product("Chum Nho", R.drawable.ic_chum_nho, false))
        list.add(Product("Qua Nho", R.drawable.ic_qua_nho, false))
        list.add(Product("Iphone SE 2020", R.drawable.ic_iphone_se_2020, false))
        list.add(Product("Iphone SE", R.drawable.ic_iphone_se, false))
        list.add(Product("Iphone XR", R.drawable.ic_iphone_xr, false))
        list.add(Product("Iphone 11", R.drawable.ic_iphone_11, false))
        list.add(Product("Iphone 12 mini", R.drawable.ic_iphone_12_mini, false))
        list.add(Product("Iphone 12", R.drawable.ic_iphone_12, false))
        list.add(Product("Iphone 12 pro max", R.drawable.ic_phone_12_pro_max_d, false))
        list.add(Product("Iphone 12 xanh", R.drawable.ic_phone12_xanh, false))
        list.add(Product("Sam sung s21 128", R.drawable.ic_sam_sung_s21_128, false))
        list.add(Product("Sam sung s21 256", R.drawable.ic_sam_sung_s21_a, false))
        list.add(Product("Sam sung zflip 3", R.drawable.ic_sam_sung_zflip_3, false))
        list.add(Product("Sam sung s21", R.drawable.ic_sam_sung_s21, false))
        list.add(Product("Sam sung z fold 3 5g", R.drawable.ic_sam_sung_z_fold_3_5g, false))
        list.add(Product("Sam sung z fold 3 5g", R.drawable.ic_sam_sung_z_fold_3_5g, false))
        list.add(Product("Sam sung galaxy a52", R.drawable.ic_galaxy_a52, false))
        list.add(Product("Sam sung z fold 3 5g", R.drawable.ic_sam_sung_z_fold_3_5g, false))
        list.add(Product("Sam sung a 31", R.drawable.ic_sam_sung_a_31, false))
        list.add(Product("Sam sung a 32", R.drawable.ic_sam_sung_a_32, false))
        list.add(Product("Sam sung galaxy m51", R.drawable.ic_sam_sung_galaxy_m51, false))
        list.add(Product("Sam sung galaxy a51", R.drawable.ic_sam_sung_galaxy_a51, false))
        list.add(Product("Sam sung galaxy a52 128", R.drawable.ic_sam_sung_galaxy_a52_2, false))
        list.add(Product("Sam sung a a52 64", R.drawable.ic_sam_sung_galaxy_a52, false))
        list.add(Product("Sam sung a 72", R.drawable.ic_sam_sung_galaxy_a72, false))
        list.add(Product("Sam sung galaxy fe", R.drawable.ic_sam_sung_galaxy_fe, false))
        list.add(Product("Sam sung galaxy note 20", R.drawable.ic_sam_sung_galaxy_note20, false))
        list.add(Product("Xiaomi redmi 11", R.drawable.ic_xiaomi_mi_11, false))
        list.add(Product("Xiaomi redmi 10", R.drawable.ic_xiao_mi_redmid_note_10, false))
        list.add(Product("Xiaomi redmi 11 128gb", R.drawable.ic_xiao_mi_redmid_note_11, false))
        list.add(Product("Xiaomi redmi 10T", R.drawable.ic_xiao_mi_redmid_note_10_t, false))
        list.add(Product("Xiaomi redmi 10PRO", R.drawable.ic_xiao_mi_redmid_note_10_pro, false))
        list.add(Product("Xiaomi redmi 10S", R.drawable.ic_xiao_mi_redmid_note_10s, false))
        list.add(Product("Xiaomi Poco FNC", R.drawable.ic_xiao_mi_redmid_poco_fnc, false))
        list.add(Product("Xiaomi redmi 9 pro", R.drawable.ic_xiao_mi_redmid_note_9_pro, false))
        list.add(Product("Xiaomi redmi 10 128gb", R.drawable.ic_xiao_mi_redmid_note_10_128, false))
        list.add(Product("Xiaomi redmmi note 9s", R.drawable.ic_xiao_mi_redmid_note_9s, false))
        list.add(Product("Xiaomi redmmi note 9", R.drawable.ic_xiao_mi_red_mi_note_9, false))
        list.add(Product("Xiaomi redmmi note 8", R.drawable.ic_xiao_mi_red_mi_note_8, false))
        list.add(Product("Xiaomi redmmi note 9T", R.drawable.ic_xiao_mi_red_mi_note_9_t, false))
        list.add(Product("Xiaomi redmmi note 9C", R.drawable.ic_xiao_mi_red_mi_note_9_c, false))
        list.add(
            Product(
                "Xiaomi redmmi note 9 64gb",
                R.drawable.ic_xiao_mi_red_mi_note_9_64,
                false
            )
        )
        list.add(Product("Xiaomi redmmi note 9A", R.drawable.ic_xiao_mi_red_mi_note_9_a, false))
        list.add(
            Product(
                "Xiaomi redmmi note 10 128",
                R.drawable.ic_xiao_mi_red_mi_note_10_128,
                false
            )
        )
        list.add(Product("Intel core i3 101000", R.drawable.ic_intel_i3_10_10000, false))
        list.add(Product("Intel core i9 109000", R.drawable.ic_intel_i9_109000, false))
        list.add(Product("Intel core i9 109000x", R.drawable.ic_intel_i9_109000x, false))
        list.add(Product("Intel core i9 109000f", R.drawable.ic_intel_i9_109000f, false))
        list.add(Product("Intel core i5 104000", R.drawable.ic_intel_i5_104000, false))
        list.add(Product("Intel core i5 104000f", R.drawable.ic_intel_i5_104000f, false))
        list.add(Product("Intel core i5 105000", R.drawable.ic_intel_i5_105000, false))
        list.add(Product("Intel core xion 4210", R.drawable.ic_intel_xion_4210, false))
        list.add(Product("Intel core i3 7100", R.drawable.ic_intel_i3_7100, false))
        list.add(Product("Intel core i7 107000k", R.drawable.ic_intel_i7_107000k, false))
        list.add(Product("AMD ryzen r5 3400g", R.drawable.ic_r5_3400g, false))
        list.add(Product("AMD ryzen r9 5900x", R.drawable.ic_r9_5900x, false))
        list.add(Product("AMD ryzen r7 57000x", R.drawable.ic_r7_5700x, false))
        list.add(Product("AMD ryzen r5 5600x", R.drawable.ic_r5_5600x, false))
        list.add(Product("AMD ryzen r5 3500", R.drawable.ic_r5_3500, false))
        list.add(Product("AMD ryzen athon 3000g", R.drawable.ic_athon_3000_g, false))
        list.add(Product("AMD ryzen r5 5600g", R.drawable.ic_r5_5600g, false))
        list.add(Product("AMD ryzen r7 2700", R.drawable.ic_r7_2700, false))
        list.add(Product("AMD ryzen r7 5700x", R.drawable.ic_r7_5700x, false))
        list.add(Product("AMD ryzen r3 3200g", R.drawable.ic_r3_3200g, false))
        list.add(
            Product(
                "Laptop dell inprison n7306 ",
                R.drawable.ic_laptop_dell_inprision_n7306,
                false
            )
        )
        list.add(Product("Laptop dell latitue 3402", R.drawable.ic_laptop_dell_latitue_3402, false))
        list.add(Product("Laptop dell vostro 340", R.drawable.ic_laptop_dell_vostro_340, false))
        list.add(
            Product(
                "Laptop dell inprision 5301",
                R.drawable.ic_laptop_dell_inprision_5301,
                false
            )
        )
        return list
    }

    override fun onItemLoveClick(value: Product, position: Int) {
        checkForSetDataToUserFragment()
    }

    private fun getListMoney(): List<Money> {
        val list = arrayListOf<Money>()
        list.add(
            Money(
                "1",
                "300,000 đồng",
                "30 like/ngày",
                "400 đồng/like",
                "3 ngày",
                "36,000 đồng",
                "Đã giới thiệu 10 người",
                "336,000 đồng"
            )
        )
        list.add(
            Money(
                "2",
                "3,000,000 đồng",
                "30 like/ngày",
                "19,280 đồng/like",
                "7 ngày",
                "945,000 đồng",
                "Đã giới thiệu 20 người",
                "3,945,000 đồng"
            )
        )
        list.add(
            Money(
                "3",
                "10,000,000 đồng",
                "30 like/ngày",
                "25,000 đồng/like",
                "20 ngày",
                "10,000,000 đồng",
                "Đã giới thiệu 30 người",
                "20,000,000 đồng"
            )
        )
        list.add(
            Money(
                "4",
                "30,000,000 đồng",
                "35 like/ngày",
                "55,000 đồng/like",
                "30 ngày",
                "49,500,000 đồng",
                "Đã giới thiệu 40 người",
                "79,500,000 đồng"
            )
        )
        list.add(
            Money(
                "5",
                "50,000,000 đồng",
                "35 like/ngày",
                "75,000 đồng/like",
                "40 ngày",
                "120,000,000 đồng",
                "Đã giới thiệu 50 người",
                "170,000,000 đồng"
            )
        )
        list.add(
            Money(
                "6",
                "100,000,000 đồng",
                "40 like/ngày",
                "162,500 đồng/like",
                "50 ngày",
                "325,000,000 đồng",
                "Đã giới thiệu 60 người",
                "425,000,000 đồng"
            )
        )
        list.add(
            Money(
                "7",
                "200,000,000 đồng",
                "40 like/ngày",
                "205,882 đồng/like",
                "68 ngày",
                "952,000,000 đồng",
                "",
                "1,152,000,000 đồng"
            )
        )
        return list
    }

    companion object {
        private const val TAG = "HomeFragment"
    }
}