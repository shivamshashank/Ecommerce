package com.example.ecommerceapp.utils

sealed class Resource<T>(val data: T? = null, val message: String? = null) {

    class Initial<T>(data: T? = null) : Resource<T>(data)
    class Loading<T>(data: T? = null) : Resource<T>(data)
    class Success<T>(data: T? = null) : Resource<T>(data)
    class Error<T>(message: String, data: T? = null) : Resource<T>(data, message)

}
