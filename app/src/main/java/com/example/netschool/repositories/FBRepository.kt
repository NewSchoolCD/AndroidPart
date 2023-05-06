package com.example.netschool.repositories

import com.example.netschool.adapters.FBTools
import com.example.netschool.model.Tutor
import com.google.firebase.auth.FirebaseUser
import javax.inject.Inject


class FBRepository @Inject constructor(val fbTools: FBTools) {

    suspend fun signUpUser(email: String, password: String): FirebaseUser? {
        return fbTools.signUpUser(email, password)
    }

    suspend fun signInUser(email: String, password: String): FirebaseUser? {
        return fbTools.signInUser(email, password)
    }

    suspend fun getCourses(): List<String> {
        return fbTools.getCources()
    }

    suspend fun getGrades(subject: String):List<String>{
        return fbTools.getGrades(subject)
    }

    suspend fun getTutor():List<Tutor>{
        return fbTools.getTutor()
    }

    suspend fun getTutor(course:String):List<Tutor>{
        return fbTools.getTutor(course)
    }
    suspend fun getTutor(course:String, grade:Int):List<Tutor>{
        return fbTools.getTutor(course, grade)
    }

    suspend fun signOut():FirebaseUser?{
        return fbTools.signOut()
    }

    fun getUser(): FirebaseUser? {
        return fbTools.getUser()
    }

    suspend fun sendForgotPassword(email: String): Boolean {
        fbTools.sendForgotPassword(email)
        return true
    }

}
