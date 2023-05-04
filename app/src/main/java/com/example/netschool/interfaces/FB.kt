package com.example.netschool.interfaces

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.CollectionReference

interface FB {
    suspend fun signUpUser(email: String, password: String):FirebaseUser?

    suspend fun signInUser(email: String, password: String):FirebaseUser?

    suspend fun signInWithGoogle(credential: AuthCredential):FirebaseUser?

    suspend fun getCources():CollectionReference

    fun saveResource(email: String, map:HashMap<String,Double>): Task<Void>
    fun signOut():FirebaseUser?

    fun getUser():FirebaseUser?

    suspend fun sendForgotPassword(email: String)
}