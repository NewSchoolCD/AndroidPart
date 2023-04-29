package com.example.netschool.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.fragment.findNavController
import com.example.netschool.R
import com.example.netschool.auth.LoginViewModel
import com.example.netschool.databinding.FragmentLoginBinding
import com.example.netschool.model.Status
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.util.TextUtils
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {


    private var _binding: FragmentLoginBinding? = null
    private val viewModel by viewModels<LoginViewModel>()
    private lateinit var auth: FirebaseAuth



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
        auth = FirebaseAuth.getInstance()

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

                }
                is Status.Success ->{

                }
                is Status.Failure -> {

                }

            }
        }
    }


}