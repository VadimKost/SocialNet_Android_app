package com.V.VSocial

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*


object SocialNetService {
    private val BASE_URL = "http://www.vako.ga/api/"
//    private val  BASE_URL="http://192.168.0.105:8000/api/"
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

    @POST("profile/contacts_and_links/")
    fun createContactAndLinks(@Body data: Contact_and_links): Call<Contact_and_links>

    @POST("profile/user/")
    fun createUser(@Body data: User): Call<User>
}

