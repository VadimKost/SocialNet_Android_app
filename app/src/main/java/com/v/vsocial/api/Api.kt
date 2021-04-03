package com.v.vsocial

import com.v.vsocial.models.ContactsLinks
import com.v.vsocial.models.User
import com.v.vsocial.utils.ResponseState
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*



interface Api {
    @GET("profile/user/currentUser/")
    suspend fun getCurrentUser(): ResponseState<User>

    @GET("profile/contacts_and_links/")
    suspend fun getContactAndLinks():ResponseState<List<ContactsLinks>>

    @POST("profile/contacts_and_links/")
    suspend fun createContactAndLinks(@Body data: ContactsLinks): ResponseState<ContactsLinks>

    @POST("profile/user/")
    suspend fun createUser(@Body data: User): ResponseState<User>
}

