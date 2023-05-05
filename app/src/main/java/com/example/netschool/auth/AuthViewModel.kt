package com.example.netschool.auth

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.netschool.model.Status
import com.example.netschool.repositories.FBRepository
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val repository: FBRepository,

    ) :ViewModel() {
    private val _fbUser = MutableLiveData<FirebaseUser?>()
    private val _userStatus:MutableLiveData<Status<FirebaseUser>> = MutableLiveData()
    val currentUser get() = _fbUser
    val currentStatus get() = _userStatus


    fun trySignInUser(email: String, password: String) = viewModelScope.launch {
        when {
            email.isEmpty() -> {
//                eventsChannel.send(AllEvents.ErrorCode(1))
            }

            password.isEmpty() -> {
//                eventsChannel.send(AllEvents.ErrorCode(2))
            }

            else -> {
                _userStatus.postValue(Status.Loading())
                signInUser(email, password)
            }
        }
    }

    fun trySignUp(name: String, email: String, password: String, comfirm:String) = viewModelScope.launch {
        when {
            name.isEmpty() -> {
//                eventsChannel.send(AllEvents.ErrorCode(1))
            }
            email.isEmpty() -> {
//                eventsChannel.send(AllEvents.ErrorCode(1))
            }

            password.isEmpty() -> {
//                eventsChannel.send(AllEvents.ErrorCode(2))
            }
            comfirm.isEmpty() -> {
//                eventsChannel.send(AllEvents.ErrorCode(2))
            }
            else -> {
                _userStatus.postValue(Status.Loading())
                signUp(email, password)
            }
        }
    }

    private fun signInUser(email: String, password: String) = viewModelScope.launch {
        try {
            val user = repository.signInUser(email, password)
            user?.let {
                _fbUser.postValue(it)
                _userStatus.postValue(Status.Success(it))

//                eventsChannel.send(AllEvents.Message("login success"))
            }
        } catch (e: Exception) {
            currentStatus.postValue(Status.Failure(e))
            val error = e.toString().split(":").toTypedArray()
            Log.d("SignIn", "signInUser: ${error[0]}")
            Log.d("typeSignIn", "TypeException: ${e::class}")
        }
    }

    private fun signUp(email: String, password: String) = viewModelScope.launch {
        try {
            val user = repository.signUpUser(email, password)
            user?.let {
                _fbUser.postValue(it)
                _userStatus.postValue(Status.Success(it))

//                eventsChannel.send(AllEvents.Message("login success"))
            }
        } catch (e: Exception) {
            currentStatus.postValue(Status.Failure(e))
            val error = e.toString().split(":").toTypedArray()
            Log.d("SignIn", "signInUser: ${error[0]}")
            Log.d("typeSignIn", "TypeException: ${e::class}")
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
        _fbUser.postValue(user)
    }

}