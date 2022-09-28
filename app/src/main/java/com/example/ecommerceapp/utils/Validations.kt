package com.example.ecommerceapp.utils

import android.util.Patterns

fun validateFirstName(firstName: String): String =
    if (firstName.isEmpty()) "First Name can not be empty"
    else ""

fun validateLastName(lastName: String): String =
    if (lastName.isEmpty()) "Last Name can not be empty"
    else ""

fun validateEmail(email: String): String {
    if (email.isBlank() || email.isEmpty())
        return "Email can not be empty"

    if (!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        return "Invalid Email"

    return ""
}

fun validatePassword(password: String): String {
    if (password.isEmpty())
        return "Password can not be empty"

    if (password.length < 8)
        return "Password is small"

    return ""
}