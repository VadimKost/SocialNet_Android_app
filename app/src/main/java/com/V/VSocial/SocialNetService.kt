package com.V.VSocial

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header


object SocialNetService {
    private val BASE_URL = "http://www.vako.ga/"
    lateinit var retrofit: Retrofit
    init {
        retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    fun Api(): JSONPlaceHolderApi {
        return retrofit.create(JSONPlaceHolderApi::class.java)
    }

}


interface JSONPlaceHolderApi {
    @GET("profile/user/currentUser/")
    fun getCurrentUser(@Header("Authorization") credentials: String?): Call<User>

    @GET("profile/contacts_and_links/")
    fun getContactAndLinks(@Header("Authorization") credentials: String?):Call<List<Contact_and_links>>
}

