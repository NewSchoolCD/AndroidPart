package com.example.netschool.adapters

import com.example.netschool.interfaces.FB
import com.example.netschool.model.Subject
import com.example.netschool.model.Tutor
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class FBTools : FB {
    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()


    override suspend fun signUpUser(
        email: String,
        password: String
    ): FirebaseUser? {
        firebaseAuth.createUserWithEmailAndPassword(email, password).await()
        return firebaseAuth.currentUser
    }

    override suspend fun signInUser(email: String, password: String): FirebaseUser? {
        firebaseAuth.signInWithEmailAndPassword(email, password).await()
        return firebaseAuth.currentUser
    }

    override suspend fun signInWithGoogle(credential: AuthCredential): FirebaseUser? {
        firebaseAuth.signInWithCredential(credential)
            .await()
        return firebaseAuth.currentUser
    }


    override fun signOut(): FirebaseUser? {
        firebaseAuth.signOut()
        return firebaseAuth.currentUser
    }

    override suspend fun getCources(): List<String> {
        val snapshot = firestore.collection("subject").get().await()
        return snapshot.documents.map { it.toObject(Subject::class.java)!!.label }
    }

    override suspend fun getGrades(course:String):List<String>{
        val snapshot = firestore.collection("subject").whereEqualTo("label", course).get().await()
        return snapshot.documents[0].toObject(Subject::class.java)!!.grades.sorted().map { it.toString() }
    }

    override suspend fun getTutor():List<Tutor>{
        val snapshot = firestore.collection("teachers").get().await()
        return snapshot.documents.map { it.toObject(Tutor::class.java)!! }
    }

    override suspend fun getTutor(course: String):List<Tutor>{
        val snapshot = firestore.collection("teachers").whereArrayContainsAny("Cources.${course}",
            listOf(7,9,11)
        ).get().await()
        return snapshot.documents.map { it.toObject(Tutor::class.java)!! }
    }
    override suspend fun getTutor(course: String, grade:Int):List<Tutor>{
        val snapshot = firestore.collection("teachers").whereArrayContainsAny("Cources.${course}",
            listOf(grade)
        ).get().await()
        return snapshot.documents.map { it.toObject(Tutor::class.java)!! }
    }



    override fun getUser(): FirebaseUser? {
        return firebaseAuth.currentUser
    }

    override suspend fun sendForgotPassword(email: String) {
        firebaseAuth.sendPasswordResetEmail(email).await()
    }





}