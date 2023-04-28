package com.example.netschool.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.netschool.R
import com.example.netschool.auth.LoginViewModel
import com.example.netschool.databinding.FragmentLoginBinding
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.util.TextUtils

class LoginFragment : Fragment() {

    companion object {
        const val TAG = "LoginFragment"
        const val SIGN_IN_RESULT_CODE = 1001
    }

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
        randomButton.setOnClickListener {
            val email: String = binding.editLoginUser.text.toString()
            val password: String = binding.editLoginPass.text.toString()
            if(TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                Toast.makeText(context, "Please fill all the fields", Toast.LENGTH_LONG).show()
            } else{
                auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(requireActivity(), OnCompleteListener { task ->
                    if(task.isSuccessful) {
                        Toast.makeText(context, "Successfully Logged In", Toast.LENGTH_LONG).show()
                        findNavController().navigate(R.id.navigation_dashboard)

                    }else {
                        Toast.makeText(context, "Login Failed", Toast.LENGTH_LONG).show()
                    }
                })
            }
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

//    private fun launchSignInFlow() {
//
//        val providers = arrayListOf(
//            AuthUI.IdpConfig.EmailBuilder().build(), AuthUI.IdpConfig.GoogleBuilder().build()
//        )
//
//        startActivityForResult(
//            AuthUI.getInstance().createSignInIntentBuilder().setAvailableProviders(
//                providers
//            ).build(), SIGN_IN_RESULT_CODE
//        )
//    }
//
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if (requestCode == SIGN_IN_RESULT_CODE) {
//            val response = IdpResponse.fromResultIntent(data)
//            if (resultCode == Activity.RESULT_OK) {
//                // Successfully signed in user.
//                Log.i(TAG, "Successfully signed in user " +
//                            "${FirebaseAuth.getInstance().currentUser?.displayName}!"
//                )
//            } else {
//                Log.i(TAG, "Sign in unsuccessful ${response?.error?.errorCode}")
//            }
//        }
//    }

}