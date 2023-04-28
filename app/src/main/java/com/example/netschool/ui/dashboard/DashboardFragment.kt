package com.example.netschool.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.netschool.R
import com.example.netschool.auth.LoginViewModel
import com.example.netschool.databinding.FragmentDashboardBinding


class DashboardFragment : Fragment(){
    private var _binding: FragmentDashboardBinding? = null
    private val viewModel by viewModels<LoginViewModel>()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root

//        val textView: TextView = binding.textDashboard
        return root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeAuthenticationState()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    private fun observeAuthenticationState() {

    }

}