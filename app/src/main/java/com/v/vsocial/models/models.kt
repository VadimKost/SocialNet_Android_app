package com.v.vsocial

import com.google.gson.annotations.SerializedName


data class Contact_and_links (
    @SerializedName("id") val id : Int?=null,
    @SerializedName("title") val title : String?=null,
    @SerializedName("link") val link : String?=null,
    @SerializedName("user") val user : Int?=null
)

