package com.v.vsocial.utils

import okhttp3.Request
import okio.Timeout
import retrofit2.*
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type


abstract class CallDelegate<In, Out>(
    protected val proxy: Call<In>
) : Call<Out> {
    override fun execute(): Response<Out> = throw NotImplementedError()
    override final fun enqueue(callback: Callback<Out>) = enqueueImpl(callback)
    override final fun clone(): Call<Out> = cloneImpl()

    override fun cancel() = proxy.cancel()
    override fun request(): Request = proxy.request()
    override fun isExecuted() = proxy.isExecuted
    override fun isCanceled() = proxy.isCanceled

    abstract fun enqueueImpl(callback: Callback<Out>)
    abstract fun cloneImpl(): Call<Out>
}

class ResponseStateCall<T>(proxy: Call<T>) : CallDelegate<T, ResponseState<T>>(proxy) {

    override fun enqueueImpl(callback: Callback<ResponseState<T>>) = proxy.enqueue(object: Callback<T> {
        override fun onResponse(call: Call<T>, response: Response<T>) {
            val code = response.code()
            val result = if (code in 200 until 300) {
                val body = response.body()
                if (body != null) {
                    ResponseState.Success(body)
                }else{
                    ResponseState.Error("Empty body")
                }
            }else if (code ==401){
                ResponseState.AuthError()
            } else {
                ResponseState.Error("UnknownProblem")
            }

            callback.onResponse(this@ResponseStateCall, Response.success(result))
        }

        override fun onFailure(call: Call<T>, t: Throwable) {
            val result = ResponseState.NetError<T>()
            callback.onResponse(this@ResponseStateCall, Response.success(result))
        }
    })

    override fun cloneImpl() = ResponseStateCall(proxy.clone())
    override fun timeout(): Timeout {
        return proxy.timeout()
    }
}

class ResponseStateAdapter(private val type: Type): CallAdapter<Type, Call<ResponseState<Type>>> {
    override fun responseType() = type
    override fun adapt(call: Call<Type>): Call<ResponseState<Type>> = ResponseStateCall(call)
}
class ResponseStateFactory : CallAdapter.Factory() {

    override fun get(returnType: Type, annotations: Array<Annotation>, retrofit: Retrofit): CallAdapter<*, *>? {
        val rawReturnType: Class<*> = getRawType(returnType)
        if (rawReturnType == Call::class.java) {
            if (returnType is ParameterizedType) {
                val callInnerType: Type = getParameterUpperBound(0, returnType)
                if (getRawType(callInnerType) == ResponseState::class.java) {
                    // resultType is Call<Result<*>> | callInnerType is Result<*>
                    if (callInnerType is ParameterizedType) {
                        val resultInnerType = getParameterUpperBound(0, callInnerType)
                        return ResponseStateAdapter(resultInnerType)
                    }
                    return ResponseStateAdapter(Nothing::class.java)
                }
            }
        }

        return null
    }
}
