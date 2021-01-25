package com.v.vsocial.models

import com.google.gson.annotations.SerializedName

data class UserProfile (
    @SerializedName("id") val id : Int?=null,
    @SerializedName("adress") val adress : String?=null,
    @SerializedName("AboutMe") val aboutMe : String?=null,
    @SerializedName("data") val data : String?=null,
    @SerializedName("gender") val gender : Int?=null,
    @SerializedName("photo") val photo : String?=null
)
