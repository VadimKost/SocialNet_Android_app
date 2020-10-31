package com.V.VSocial

import com.google.gson.annotations.SerializedName

data class User (
    @SerializedName("id") val id : Int,
    @SerializedName("user_profile") val user_profile : User_profile,
    @SerializedName("username") val username : String,
    @SerializedName("first_name") val first_name : String,
    @SerializedName("last_name") val last_name : String,
    @SerializedName("email") val email : String
)

data class User_profile (
    @SerializedName("id") val id : Int,
    @SerializedName("adress") val adress : String,
    @SerializedName("AboutMe") val aboutMe : String,
    @SerializedName("data") val data : String,
    @SerializedName("gender") val gender : Int,
    @SerializedName("photo") val photo : String
)

data class Contact_and_links (
    @SerializedName("id") val id : Int,
    @SerializedName("title") val title : String,
    @SerializedName("link") val link : String,
    @SerializedName("user") val user : Int
)