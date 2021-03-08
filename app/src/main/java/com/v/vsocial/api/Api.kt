package com.v.vsocial

import com.v.vsocial.models.ContactsLinks
import com.v.vsocial.models.User
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*



interface Api {
    @GET("profile/user/currentUser/")
    suspend fun getCurrentUser(@Header("Authorization") credentials: String?): Response<User>

    @GET("profile/contacts_and_links/")
    suspend fun getContactAndLinks(@Header("Authorization") credentials: String?):Response<List<ContactsLinks>>

    @POST("profile/contacts_and_links/")
    suspend fun createContactAndLinks(@Body data: ContactsLinks): Response<ContactsLinks>

    @POST("profile/user/")
    suspend fun createUser(@Body data: User): Response<User>
}

