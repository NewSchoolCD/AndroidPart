package com.example.netschool.auth

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.example.netschool.repositories.FBRepository
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.launch

class LoginViewModel (
    private val repository: FBRepository,

    ) :ViewModel() {
    private val fbUser = MutableLiveData<FirebaseUser?>()
    private val vkEmail = MutableLiveData<String?>()
    val currentUser get() = fbUser
    val vkAuthEmail get() = vkEmail


    fun signInUser(email: String, password: String) = viewModelScope.launch {
        when {
            email.isEmpty() -> {
//                eventsChannel.send(AllEvents.ErrorCode(1))
            }

            password.isEmpty() -> {
//                eventsChannel.send(AllEvents.ErrorCode(2))
            }

            else -> {
                actualSignInUser(email, password)
            }
        }
    }

    private fun actualSignInUser(email: String, password: String) = viewModelScope.launch {
        try {
            val user = repository.signInUser(email, password)
            user?.let {
                fbUser.postValue(it)

//                eventsChannel.send(AllEvents.Message("login success"))
            }
        } catch (e: Exception) {

            val error = e.toString().split(":").toTypedArray()
            Log.d("SignIn", "signInUser: ${error[1]}")
            Log.d("typeSignIn", "TypeException: ${e::class}")
//            eventsChannel.send(AllEvents.Error(error[1]))
        }
    }
    fun verifySendPasswordReset(email: String) {
        if (email.isEmpty()) {
            viewModelScope.launch {
//                eventsChannel.send(AllEvents.ErrorCode(1))
            }
        } else {
            sendPasswordResetEmail(email)
        }

    }

    private fun sendPasswordResetEmail(email: String) = viewModelScope.launch {
        try {
            val result = repository.sendForgotPassword(email)
            if (result) {
//                eventsChannel.send(AllEvents.Message("reset email sent"))
            } else {
//                eventsChannel.send(AllEvents.Error("could not send password reset"))
            }
        } catch (e: Exception) {
            val error = e.toString().split(":").toTypedArray()
//            Log.d(TAG, "signInUser: ${error[1]}")
//            eventsChannel.send(AllEvents.Error(error[1]))
        }
    }

    fun getUser() = viewModelScope.launch {
        val user = repository.getUser()
        fbUser.postValue(user)
    }

}