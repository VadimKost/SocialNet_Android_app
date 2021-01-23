package com.v.vsocial.models

import com.google.gson.annotations.SerializedName

data class ContactsLinks(
    @SerializedName("id") val id : Int?=null,
    @SerializedName("title") val title : String?=null,
    @SerializedName("link") val link : String?=null,
    @SerializedName("user") val user : Int?=null
)