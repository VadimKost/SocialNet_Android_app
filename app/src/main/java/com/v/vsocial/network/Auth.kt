package com.v.vsocial.network

import android.content.Context
import android.util.Patterns
import okhttp3.Credentials


object Auth {
    fun getUserCredentials(context: Context?): String? {
        return context?.getSharedPreferences("Settings", Context.MODE_PRIVATE)?.getString(
            "UAC",
            null
        )
    }

    fun setUserCredentials(context: Context?, username: String, password: String){
        context?.getSharedPreferences("Settings", Context.MODE_PRIVATE)?.edit()
            ?.putString("UAC", Credentials.basic(username, password))?.apply()
    }
    fun removeUserCredentials(context: Context?){
        context?.getSharedPreferences("Settings", Context.MODE_PRIVATE)?.edit()?.remove("UAC")?.apply()
    }

    fun isValidEmail(email: String): Boolean {
        val pattern = Patterns.EMAIL_ADDRESS
        return pattern.matcher(email).matches()
    }
}