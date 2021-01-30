package com.v.vsocial.utils

sealed class ResponseState<T>(
    val data: T? = null,
) {
    class Success<T>(data: T) : ResponseState<T>(data)
    class NetError<T>(data: T? = null) : ResponseState<T>(data)
    class UnknownProblem<T>() : ResponseState<T>()
    class AuthError<T>() : ResponseState<T>()
}