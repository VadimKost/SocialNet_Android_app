package com.v.vsocial.api.auth

import android.content.Context
import android.util.Patterns
import com.v.vsocial.Api
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.Credentials
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Auth @Inject constructor(
    @ApplicationContext val context: Context
    ) {
    fun getUserCredentials(): String? {
        return context.getSharedPreferences("Settings", Context.MODE_PRIVATE)?.getString(
            "UAC",
            ""
        )
    }

    fun setUserCredentials(username: String, password: String){
        context.getSharedPreferences("Settings", Context.MODE_PRIVATE).edit()
            .putString("UAC", Credentials.basic(username, password)).apply()
    }

    fun removeUserCredentials(){
        context.getSharedPreferences("Settings", Context.MODE_PRIVATE).edit().remove("UAC").apply()
    }

    fun isValidEmail(email: String): Boolean {
        val pattern = Patterns.EMAIL_ADDRESS
        return pattern.matcher(email).matches()
    }
}