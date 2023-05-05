package com.example.netschool.ui.register

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
import com.example.netschool.databinding.FragmentRegisterBinding
import com.example.netschool.model.Status
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val viewModel by viewModels<AuthViewModel>()


    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.textView8.setOnClickListener {
            findNavController().navigate(R.id.action_registerFragment_to_navigation_login)
        }


        return root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val randomButton = binding.register
        var job: Job? = null
        randomButton.setOnClickListener {
            job?.cancel()
            job = MainScope().launch {
                viewModel.trySignUp(
                    binding.editTextText.text.toString(),
                    binding.editTextText1.text.toString(),
                    binding.editTextText2.text.toString(),
                    binding.editTextText3.text.toString()
                )
            }
        }
        provideObservers()
    }



    private fun provideObservers() {
        viewModel.currentStatus.observe(viewLifecycleOwner) {
            when (it) {
                is Status.Loading -> {
//                    binding.progressBar.visibility = View.VISIBLE
                }

                is Status.Success -> {
//                    binding.progressBar.visibility = View.GONE
                    findNavController().navigate(R.id.action_navigation_register_to_navigation_dashboard)
                }

                is Status.Failure -> {
//                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(context, it.exception.localizedMessage, Toast.LENGTH_SHORT).show()
                    Log.d("LoginError", it.exception.message.toString())

                }

            }
        }
    }

}
