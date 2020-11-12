package com.V.VSocial

import android.content.Context
import com.google.gson.annotations.SerializedName
import okhttp3.Credentials
import java.util.regex.Pattern

data class User (
    @SerializedName("id") val id : Int?=null,
    @SerializedName("user_profile") val user_profile : User_profile,
    @SerializedName("username") val username : String?=null,
    @SerializedName("first_name") val first_name : String?=null,
    @SerializedName("last_name") val last_name : String?=null,
    @SerializedName("email") val email : String?=null,
    @SerializedName("password") val password : String?=null
)

data class User_profile (
    @SerializedName("id") val id : Int?=null,
    @SerializedName("adress") val adress : String?=null,
    @SerializedName("AboutMe") val aboutMe : String?=null,
    @SerializedName("data") val data : String?=null,
    @SerializedName("gender") val gender : Int?=3,
    @SerializedName("photo") val photo : String?=null
)

data class Contact_and_links (
    @SerializedName("id") val id : Int?=null,
    @SerializedName("title") val title : String?=null,
    @SerializedName("link") val link : String?=null,
    @SerializedName("user") val user : Int?=null
)

fun getUserCredentials(context: Context?): String? {
    return context?.getSharedPreferences("Settings", Context.MODE_PRIVATE)?.getString("UAC",null)
}

fun setUserCredentials(context: Context?,username: String,password: String){
    context?.getSharedPreferences("Settings", Context.MODE_PRIVATE)?.edit()
        ?.putString("UAC", Credentials.basic(username, password))?.apply()
}
fun removeUserCredentials(context: Context?){
    context?.getSharedPreferences("Settings", Context.MODE_PRIVATE)?.edit()?.remove("UAC")?.apply()
}

val EMAIL_ADDRESS_PATTERN: Pattern = Pattern.compile(
    "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
            "\\@" +
            "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
            "(" +
            "\\." +
            "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
            ")+"
)