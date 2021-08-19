package com.subi.likeanh.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.subi.likeanh.BR
import com.subi.likeanh.R
import com.subi.likeanh.adapter.HomeAdapter
import com.subi.likeanh.databinding.FragmentHomeBinding
import com.subi.likeanh.model.Product

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private val viewModel: HomeViewModel by viewModels()
    private var list = arrayListOf<Product>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        init()

        binding.apply {
            rcv.apply {
                adapter = HomeAdapter(getDataForRecyclerView())
                layoutManager = GridLayoutManager(requireActivity(), 2)
                hasFixedSize()
            }
        }

        return binding.root;
    }

    fun init() {
        binding.setVariable(BR.viewModel, viewModel)
        requireActivity().findViewById<BottomNavigationView>(R.id.bottom_nav).visibility =
            View.VISIBLE
    }

    private fun getDataForRecyclerView(): List<Product> {
        var list = arrayListOf<Product>()
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
        list.add(Product("Xiaomi redmmi note 9 64gb", R.drawable.ic_xiao_mi_red_mi_note_9_64, false))
        list.add(Product("Xiaomi redmmi note 9A", R.drawable.ic_xiao_mi_red_mi_note_9_a, false))
        list.add(Product("Xiaomi redmmi note 10 128", R.drawable.ic_xiao_mi_red_mi_note_10_128, false))

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

        list.add(Product("Laptop dell inprison n7306 ", R.drawable.ic_laptop_dell_inprision_n7306, false))
        list.add(Product("Laptop dell latitue 3402", R.drawable.ic_laptop_dell_latitue_3402, false))
        list.add(Product("Laptop dell vostro 340", R.drawable.ic_laptop_dell_vostro_340, false))
        list.add(Product("Laptop dell inprision 5301", R.drawable.ic_laptop_dell_inprision_5301, false))
        list.add(Product("Laptop dell g5 5500a", R.drawable.ic_laptop_dell_gaming_g5_5500a, false))
        list.add(Product("Laptop dell vostro 5502", R.drawable.ic_laptop_dell_vostro_5502, false))
        list.add(Product("Laptop dell vps 9310", R.drawable.ic_laptop_dell_xps_9310, false))
        list.add(Product("Laptop dell vostro 3400", R.drawable.ic_laptop_dell_vostro_3400, false))
        list.add(Product("Laptop dell vostro 3400", R.drawable.ic_laptop_dell_vostro_3400, false))
        list.add(Product("Laptop dell vostros 3405", R.drawable.ic_laptop_dell_vostro_3405, false))




        return list
    }
}