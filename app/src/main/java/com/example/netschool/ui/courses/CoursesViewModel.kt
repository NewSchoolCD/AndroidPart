@file:OptIn(ExperimentalCoroutinesApi::class)

package com.example.netschool.ui.courses

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.netschool.model.Subject
import com.example.netschool.repositories.FBRepository
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.ktx.dataObjects
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@HiltViewModel
class CoursesViewModel @Inject constructor(
    private val repository: FBRepository,
) : ViewModel() {
    val subjects: MutableLiveData<List<Subject>> = MutableLiveData()
    init {
        viewModelScope.launch {
            getCourses()
        }
    }
    suspend fun getCourses()  {
        repository.getCourses().get().await().forEach {
            Log.d("FBObject",it.toObject(Subject::class.java).label)
        }
    }

}