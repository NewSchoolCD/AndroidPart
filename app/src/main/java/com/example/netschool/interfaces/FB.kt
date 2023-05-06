package com.example.netschool.interfaces

import com.example.netschool.model.Tutor
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseUser

interface FB {
    suspend fun signUpUser(email: String, password: String):FirebaseUser?

    suspend fun signInUser(email: String, password: String):FirebaseUser?

    suspend fun signInWithGoogle(credential: AuthCredential):FirebaseUser?

    suspend fun getCources(): List<String>

    suspend fun getGrades(course:String):List<String>

    suspend fun getTutor():List<Tutor>

    suspend fun getTutor(course: String):List<Tutor>

    suspend fun getTutor(course: String, grade:Int):List<Tutor>


    fun signOut():FirebaseUser?

    fun getUser():FirebaseUser?

    suspend fun sendForgotPassword(email: String)
}