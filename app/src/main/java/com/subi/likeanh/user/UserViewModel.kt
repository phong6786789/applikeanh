package com.subi.likeanh.user

import androidx.databinding.ObservableField
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import com.subi.likeanh.model.Money
import com.subi.likeanh.model.User
import com.subi.likeanh.money.nap.NapViewModel

class UserViewModel : ViewModel() {

    var user: ObservableField<User> = ObservableField()

}