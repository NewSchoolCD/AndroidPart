package com.example.netschool.repositories

import androidx.activity.ComponentActivity
import com.example.netschool.adapters.FBTools
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseUser


class FBRepository(val fbTools: FBTools) {

    suspend fun signUpUser(email: String, password: String): FirebaseUser? {
        return fbTools.signUpUser(email, password)
    }

    suspend fun signInUser(email: String, password: String): FirebaseUser? {
        return fbTools.signInUser(email, password)
    }

    suspend fun signInWithGoogle(credential: AuthCredential) : FirebaseUser? {
        return fbTools.signInWithGoogle(credential)
    }


    fun signOut():FirebaseUser?{
        return fbTools.signOut()
    }

    fun getUser(): FirebaseUser? {
        return fbTools.getUser()
    }

    suspend fun sendForgotPassword(email: String): Boolean {
        fbTools.sendForgotPassword(email)
        return true
    }

    fun saveResource(email: String, map:HashMap<String,Double>): Task<Void> {
        return fbTools.saveResource(email,map)
    }
}
