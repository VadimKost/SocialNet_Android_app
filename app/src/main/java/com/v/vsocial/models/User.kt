package com.v.vsocial.models

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("id") val id: Int? =null,
    @SerializedName("user_profile") val user_profile: UserProfile,
    @SerializedName("username") val username: String?=null,
    @SerializedName("first_name") val first_name: String?=null,
    @SerializedName("last_name") val last_name: String?=null,
    @SerializedName("email") val email: String?=null,
    @SerializedName("password") val password: String?=null
)


