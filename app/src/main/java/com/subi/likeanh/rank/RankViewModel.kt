package com.subi.likeanh.rank

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.subi.likeanh.model.User

class RankViewModel : ViewModel() {

    var user: ObservableField<User> = ObservableField()

}