package com.v.vsocial.utils

sealed class ResponseState<out T>(
) {
    class Success<T>(val data: T) : ResponseState<T>()
    class Offline<T>(var data: T? = null) : ResponseState<T>()
    class Error<T>(val message:String?=null) : ResponseState<T>()
    class AuthError<T>() : ResponseState<T>()
}