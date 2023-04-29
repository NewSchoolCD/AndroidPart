package com.example.netschool.ui.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.netschool.R
import com.example.netschool.databinding.FragmentSplashBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashFragment : Fragment() {
    private var _binding: FragmentSplashBinding? = null
    private val viewModel: SplashViewModel by viewModels()

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment

        _binding = FragmentSplashBinding.inflate(inflater, container, false)

        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerObservers()
        getUser()
    }
    private fun registerObservers() {
        viewModel.currentUser.observe(viewLifecycleOwner) { user ->
            if (user!=null) {
                findNavController().navigate(R.id.action_splashFragment_to_navigation_dashboard)
            }
            else{
                findNavController().navigate(R.id.action_splashFragment_to_navigation_login)

            }
        }
    }
    fun getUser(){
        viewModel.getUser()
    }

}