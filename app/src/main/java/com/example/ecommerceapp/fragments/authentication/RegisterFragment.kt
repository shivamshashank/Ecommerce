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
import com.example.ecommerceapp.data.User
import com.example.ecommerceapp.databinding.FragmentRegisterBinding
import com.example.ecommerceapp.utils.*
import com.example.ecommerceapp.utils.Constants.UNKNOWN_ERROR
import com.example.ecommerceapp.view_model.AuthenticationViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment : Fragment(R.layout.fragment_register) {
    private lateinit var binding: FragmentRegisterBinding

    private val viewModel by viewModels<AuthenticationViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterBinding.inflate(inflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            textViewDoYouHaveAccount.setOnClickListener {
                findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
            }

            buttonRegister.setOnClickListener {
                val firstName = editTextFirstName.text.toString().trim()
                val lastName = editTextLastName.text.toString().trim()
                val email = editTextEmail.text.toString().trim()
                val password = editTextPassword.text.toString()

                val firstNameValidation = validateFirstName(firstName)
                val lastNameValidation = validateLastName(lastName)
                val emailValidation = validateEmail(email)
                val passwordValidation = validatePassword(password)

                if (firstNameValidation != "") {
                    editTextFirstName.requestFocus()
                    editTextFirstName.error = firstNameValidation
                } else if (lastNameValidation != "") {
                    editTextLastName.requestFocus()
                    editTextLastName.error = lastNameValidation
                } else if (emailValidation != "") {
                    editTextEmail.requestFocus()
                    editTextEmail.error = emailValidation
                } else if (passwordValidation != "") {
                    editTextPassword.requestFocus()
                    editTextPassword.error = passwordValidation
                } else {
                    val user = User(
                        firstName,
                        lastName,
                        email,
                    )

                    viewModel.registerWithEmailAndPassword(user, password)
                }
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.register.collect {
                when (it) {
                    is Resource.Initial -> Unit
                    is Resource.Loading -> {
                        binding.buttonRegister.startAnimation()
                    }
                    is Resource.Success -> {
                        binding.buttonRegister.revertAnimation()
                        Intent(requireActivity(), ShoppingActivity::class.java).also { intent ->
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                            startActivity(intent)
                        }
                    }
                    is Resource.Error -> {
                        binding.buttonRegister.revertAnimation()
                        Toast.makeText(
                            requireContext(),
                            it.message ?: UNKNOWN_ERROR,
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }
        }

    }
}