package com.subi.likeanh

import android.os.Bundle
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.subi.likeanh.databinding.ActivityMainBinding
import com.subi.likeanh.utils.SendTelegram


class MainActivity : AppCompatActivity() {
    var ref = FirebaseDatabase.getInstance().getReference("noti")
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initBottom()



        //run text, chạy 3 lần xong tự động tắt
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val mess = snapshot.child("mess").getValue(String::class.java)
                val status = snapshot.child("status").getValue(Boolean::class.java)
                //Hiện cho chạy 3 lần r tắt
                if (status == true) {
                    binding.scrollNoti.startAnimation(AnimationUtils.loadAnimation(this@MainActivity,
                        R.anim.anim_top_to_bot))
                    binding.isNoti = true
                    binding.mess = mess
                    binding.tvNoti.startAnimation(AnimationUtils.loadAnimation(this@MainActivity,
                        R.anim.scroll_text))
                } else {
                    binding.scrollNoti.startAnimation(AnimationUtils.loadAnimation(this@MainActivity,
                        R.anim.anim_bot_to_top))
                    binding.isNoti = false
                    binding.tvNoti.clearAnimation()
                }

            }

            override fun onCancelled(error: DatabaseError) {
            }

        })

    }

    private fun initBottom() {
        //hide bottom navigation
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment?
        val navController = navHostFragment!!.navController
        binding.bottomNav.setupWithNavController(navController)
    }
}