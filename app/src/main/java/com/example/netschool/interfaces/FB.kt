package com.example.netschool.interfaces

import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseUser

interface FB {
    suspend fun signUpUser(email: String, password: String):FirebaseUser?

    suspend fun signInUser(email: String, password: String):FirebaseUser?

    suspend fun signInWithGoogle(credential: AuthCredential):FirebaseUser?

    suspend fun getCources(): List<String>

    suspend fun getGrades(course:String):List<String>

    fun signOut():FirebaseUser?

    fun getUser():FirebaseUser?

    suspend fun sendForgotPassword(email: String)
}