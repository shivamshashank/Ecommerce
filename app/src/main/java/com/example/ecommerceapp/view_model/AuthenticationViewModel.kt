package com.example.ecommerceapp.view_model

import androidx.lifecycle.ViewModel
import com.example.ecommerceapp.data.User
import com.example.ecommerceapp.utils.Constants.UNKNOWN_ERROR
import com.example.ecommerceapp.utils.Constants.USER
import com.example.ecommerceapp.utils.Resource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject

@HiltViewModel
class AuthenticationViewModel @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firebaseFirestore: FirebaseFirestore,
) : ViewModel() {
    private val _register = MutableStateFlow<Resource<User>>(Resource.Initial())
    val register: MutableStateFlow<Resource<User>> = _register

    private val _login = MutableStateFlow<Resource<FirebaseUser>>(Resource.Initial())
    val login: MutableStateFlow<Resource<FirebaseUser>> = _login

    private val _resetPassword = MutableStateFlow<Resource<String>>(Resource.Initial())
    val resetPassword: MutableStateFlow<Resource<String>> = _resetPassword

    fun registerWithEmailAndPassword(user: User, password: String) {
        _register.value = Resource.Loading()

        firebaseAuth.createUserWithEmailAndPassword(user.email, password)
            .addOnSuccessListener { authResult ->
                if (authResult.user == null) {
                    _register.value = Resource.Error("Firebase User is null")
                } else {
                    authResult.user?.let {
                        saveUserInfoFireStore(it.uid, user)
                    }
                }
            }
            .addOnFailureListener {
                _register.value = Resource.Error(it.message ?: "No Error Message")
            }
    }

    private fun saveUserInfoFireStore(uid: String, user: User) {
        firebaseFirestore
            .collection(USER)
            .document(uid)
            .set(user)
            .addOnSuccessListener {
                _register.value = Resource.Success(user)
            }
            .addOnFailureListener {
                _register.value = Resource.Error(it.message ?: UNKNOWN_ERROR)
            }
    }

    fun loginWithEmailAndPassword(email: String, password: String) {
        _login.value = Resource.Loading()

        firebaseAuth
            .signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                if (it == null || it.user == null) {
                    _login.value = Resource.Error("AuthResult is Null")
                } else {
                    _login.value = Resource.Success(it.user)
                }
            }
            .addOnFailureListener {
                _login.value = Resource.Error(it.message ?: "")
            }
    }

    fun resetPassword(email: String) {
        _resetPassword.value = Resource.Loading()

        firebaseAuth
            .sendPasswordResetEmail(email)
            .addOnSuccessListener {
                _resetPassword.value = Resource.Success(email)
            }
            .addOnFailureListener {
                _resetPassword.value = Resource.Error(it.message ?: UNKNOWN_ERROR)
            }
    }
}