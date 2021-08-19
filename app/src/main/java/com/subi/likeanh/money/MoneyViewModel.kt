package com.subi.likeanh.money

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.subi.likeanh.model.User

class MoneyViewModel :ViewModel() {
    var user = ObservableField<User>()
    var ref  = FirebaseDatabase.getInstance().getReference("user")


    fun load(){
        ref.child(FirebaseAuth.getInstance().currentUser?.uid.toString()).addListenerForSingleValueEvent(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val value = snapshot.getValue(User::class.java)
                user.set(value)
            }

            override fun onCancelled(error: DatabaseError) {
            }

        })
    }
}