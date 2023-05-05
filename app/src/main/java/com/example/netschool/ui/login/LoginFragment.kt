package com.example.netschool.ui.login

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.netschool.R
import com.example.netschool.auth.AuthViewModel
import com.example.netschool.databinding.FragmentLoginBinding
import com.example.netschool.model.Status
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {


    private var _binding: FragmentLoginBinding? = null
    private val viewModel by viewModels<AuthViewModel>()



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

        val randomButton = binding.loginBtn
        var job: Job? = null
        randomButton.setOnClickListener {
            job?.cancel()
            job = MainScope().launch {
                viewModel.trySignInUser(binding.editLoginUser.text.toString(), binding.editLoginPass.text.toString())
            }
        }
        binding.textView8.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_login_to_registerFragment)
        }
        provideObservers()
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun provideObservers(){
        viewModel.currentStatus.observe(viewLifecycleOwner) {
            when (it) {
                is Status.Loading ->{
                    binding.progressBar.visibility = View.VISIBLE
                }
                is Status.Success ->{
                    binding.progressBar.visibility = View.GONE
                    findNavController().navigate(R.id.action_navigation_login_to_navigation_dashboard)
                }
                is Status.Failure -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(context, it.exception.localizedMessage, Toast.LENGTH_SHORT).show()
                    Log.d("LoginError", it.exception.message.toString())

                }

            }
        }
    }


}