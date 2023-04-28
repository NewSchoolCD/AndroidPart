package com.example.netschool.ui.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.netschool.repositories.FBRepository

class SplashViewModelProviderFactory  (
    val repository: FBRepository
//    val firebaseAuth: FirebaseAuth,
//    val netWorkAdapter: NetWorkAdapter
): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return SplashViewModel(repository) as T
    }
}