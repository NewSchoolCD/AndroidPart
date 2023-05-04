@file:OptIn(ExperimentalCoroutinesApi::class)

package com.example.netschool.ui.courses

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.netschool.repositories.FBRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

@HiltViewModel
class CoursesViewModel @Inject constructor(
    private val repository: FBRepository,
) : ViewModel() {
    val subjects: MutableLiveData<List<String>> = MutableLiveData()
    val grades: MutableLiveData<List<String>> = MutableLiveData()

    suspend fun getCourses()  {
        subjects.postValue(repository.getCourses())
    }

    suspend fun getGrades(subject:String){
        grades.postValue(repository.getGrades(subject))
    }

}