package com.V.VSocial

import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header


object SocialService {
    private val BASE_URL = "http://www.vako.ga/api/"
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
    @GET("currentuser/")
    fun getCurrentUser(@Header("Authorization") credentials:String): Call<User>
}


data class User (
    @SerializedName("id") val id : Int,
    @SerializedName("chats") val chats : List<String>,
    @SerializedName("img") val img : Img,
    @SerializedName("user_i") val user_i : User_i,
    @SerializedName("username") val username : String,
    @SerializedName("first_name") val first_name : String,
    @SerializedName("last_name") val last_name : String,
    @SerializedName("email") val email : String,
    @SerializedName("groups") val groups : List<String>,
    @SerializedName("user_permissions") val user_permissions : List<String>
)
data class User_i (
    @SerializedName("id") val id : Int,
    @SerializedName("adress") val adress : String,
    @SerializedName("phone") val phone : String,
    @SerializedName("AboutMe") val aboutMe : String,
    @SerializedName("data") val data : String
)
data class Img (
    @SerializedName("id") val id : Int,
    @SerializedName("photo") val photo : String,
    @SerializedName("data") val data : String,
    @SerializedName("user") val user : Int
)