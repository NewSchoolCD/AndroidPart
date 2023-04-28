package com.example.netschool.ui.splash

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.netschool.repositories.FBRepository
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.launch

class SplashViewModel(
    private val repository: FBRepository,
//    private val networkControl: NetWorkAdapter,
//    private val firebaseAuth: FirebaseAuth
) :
    ViewModel() {
    private val fbUser = MutableLiveData<FirebaseUser?>()
    val currentUser get() = fbUser

    fun getUser() = viewModelScope.launch {
        val user = repository.getUser()
        fbUser.postValue(user)
    }

}