package com.v.vsocial.repository

import android.content.Context
import com.v.vsocial.Api
import com.v.vsocial.api.Auth
import com.v.vsocial.models.User
import com.v.vsocial.utils.ResponseState
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.Credentials
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserProfileRepository @Inject constructor (
    var api: Api,
    @ApplicationContext var context:Context
    ) {

    suspend fun getCurrentUser(username:String="", password:String=""):ResponseState<User>{
        try {
        val user = if (username != "" && password != ""){
            api.getCurrentUser(Credentials.basic(username, password))
        }else{
            api.getCurrentUser(Auth.getUserCredentials(context))
        }

        if (user.code()==401){
            Auth.removeUserCredentials(context)
            return ResponseState.AuthError()
        }
        if(user.code()==200){
            if (username != "" && password != ""){
                Auth.setUserCredentials(context, username, password)
            }
            return ResponseState.Success(user.body()!!)
        }
            return ResponseState.UnknownProblem()
        }catch (t:Throwable){
            return ResponseState.NetError()
        }
    }
}