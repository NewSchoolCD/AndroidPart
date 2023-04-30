package com.example.netschool.ui.settings

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.netschool.R
import com.example.netschool.databinding.FragmentSettingsBinding
import com.github.dhaval2404.imagepicker.ImagePicker
import com.makeramen.roundedimageview.RoundedImageView
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingsFragment : Fragment() {
    lateinit var logOut:Button
    private var _binding: FragmentSettingsBinding? = null
    private val viewModel:SettingsViewModel by viewModels()
    private lateinit var accountPic:RoundedImageView
    private lateinit var startForProfileImageResult:ActivityResultLauncher<Intent>


    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        val root: View = binding.root
        accountPic = binding.roundedImageView


        startForProfileImageResult =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
                val resultCode = result.resultCode
                val data = result.data

                when (resultCode) {
                    Activity.RESULT_OK -> {
                        //Image Uri will not be null for RESULT_OK
                        val fileUri = data?.data!!

                        //                    mProfileUri = fileUri
                        accountPic.setImageURI(fileUri)
//                        uploadFirebasePic(fileUri)
                    }

                    ImagePicker.RESULT_ERROR -> {
                        Toast.makeText(context, ImagePicker.getError(data), Toast.LENGTH_SHORT)
                            .show()
                    }

                    else -> {
                        Toast.makeText(context, "Task Cancelled", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        logOut = binding.logOut
        var job: Job? = null
        logOut.setOnClickListener {
            job?.cancel()
            job = MainScope().launch {
                viewModel.logout()
                findNavController().navigate(R.id.action_navigation_settings_to_navigation_login)
            }
        }
        observeAuthenticationState()
//        val textView: TextView = binding.textSettings
//        textView.text = "Hello"

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun createImagePicker(startForProfileImageResult: ActivityResultLauncher<Intent>) {
        ImagePicker.with(this)
            .cropSquare()                    //Crop image(Optional), Check Customization for more option
            .compress(1024)            //Final image size will be less than 1 MB(Optional)
            .maxResultSize(
                720,
                720
            )    //Final image resolution will be less than 1080 x 1080(Optional)
            .createIntent { intent ->
                startForProfileImageResult.launch(intent)
            }
    }

    private fun observeAuthenticationState() {
//        viewModel.authenticationState.observe(viewLifecycleOwner, Observer { authenticationState ->
//            if (authenticationState == AuthViewModel.AuthenticationState.AUTHENTICATED) {
//
//                accountPic.setOnClickListener {
//                    createImagePicker(startForProfileImageResult)
//                }
//                binding.logOut.setOnClickListener {
//                    auth.signOut()
//                    findNavController().popBackStack()
//
//                }
//            }
//        })
    }
//    private fun uploadFirebasePic(fileUri:Uri){
//        var storageRef = firebase.storage().ref(user + '/profilePicture/' + file.name);
//
//        auth
//
//    }

}