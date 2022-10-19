package com.example.ecommerceapp.fragments.authentication

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.ecommerceapp.R
import com.example.ecommerceapp.activities.ShoppingActivity
import com.example.ecommerceapp.databinding.FragmentLoginBinding
import com.example.ecommerceapp.dialogs.setUpBottomSheetDialog
import com.example.ecommerceapp.utils.Constants.UNKNOWN_ERROR
import com.example.ecommerceapp.utils.Resource
import com.example.ecommerceapp.utils.validateEmail
import com.example.ecommerceapp.utils.validatePassword
import com.example.ecommerceapp.view_model.AuthenticationViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment(R.layout.fragment_login) {
    private lateinit var binding: FragmentLoginBinding

    private val viewModel by viewModels<AuthenticationViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            textViewDontHaveAnAccount.setOnClickListener {
                findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
            }

            buttonLogin.setOnClickListener {
                val email = editTextEmail.text.toString().trim()
                val password = editTextPassword.text.toString()

                val emailValidation = validateEmail(email)
                val passwordValidation = validatePassword(password)

                if (emailValidation != "") {
                    editTextEmail.requestFocus()
                    editTextEmail.error = emailValidation
                } else if (passwordValidation != "") {
                    editTextPassword.requestFocus()
                    editTextPassword.error = passwordValidation
                } else {
                    viewModel.loginWithEmailAndPassword(email, password)
                }
            }

            textViewForgotPassword.setOnClickListener {
                setUpBottomSheetDialog { email ->
                    viewModel.resetPassword(email)
                }
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.login.collect {
                when (it) {
                    is Resource.Initial -> Unit
                    is Resource.Loading -> {
                        binding.buttonLogin.startAnimation()
                    }
                    is Resource.Success -> {
                        binding.buttonLogin.revertAnimation()
                        Intent(requireActivity(), ShoppingActivity::class.java).also { intent ->
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                            startActivity(intent)
                        }
                    }
                    is Resource.Error -> {
                        binding.buttonLogin.revertAnimation()
                        Toast.makeText(
                            requireContext(),
                            it.message ?: UNKNOWN_ERROR,
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.resetPassword.collect {
                when (it) {
                    is Resource.Initial -> Unit
                    is Resource.Loading -> Unit
                    is Resource.Success -> {
                        Toast.makeText(
                            requireContext(),
                            "Reset password link send to email successfully!",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                    is Resource.Error -> {
                        Toast.makeText(requireContext(), it.message.toString(), Toast.LENGTH_LONG)
                            .show()
                    }
                }
            }
        }
    }
}