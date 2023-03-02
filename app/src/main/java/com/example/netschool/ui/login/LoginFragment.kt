package com.example.netschool.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.netschool.auth.LoginViewModel
import com.example.netschool.databinding.FragmentLoginBinding

class LoginFragment: Fragment() {

    companion object {
        const val TAG = "LoginFragment"
        const val SIGN_IN_RESULT_CODE = 1001
    }

    private var _binding: FragmentLoginBinding? = null
    private val viewModel by viewModels<LoginViewModel>()


    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        val root: View = binding.root

        


//        val textView: TextView = binding.textSettings
//        textView.text = "Hello"

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }



}