package com.v.vsocial

import com.v.vsocial.models.ContactsLinks
import com.v.vsocial.models.User
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*


object SocialNet {
    private val  BASE_URL="http://192.168.42.93:8000/api/"
//    private val BASE_URL = "http://www.vako.ga/api/"
//    private val  BASE_URL="http://192.168.0.106:8000/api/"
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
    suspend fun getCurrentUser(@Header("Authorization") credentials: String?): Response<User>

    @GET("profile/contacts_and_links/")
    suspend fun getContactAndLinks(@Header("Authorization") credentials: String?):Response<List<ContactsLinks>>

    @POST("profile/contacts_and_links/")
    suspend fun createContactAndLinks(@Body data: ContactsLinks): Response<ContactsLinks>

    @POST("profile/user/")
    suspend fun createUser(@Body data: User): Response<User>
}

