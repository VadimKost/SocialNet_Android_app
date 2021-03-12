package com.v.vsocial.repository

import android.content.Context
import com.v.vsocial.Api
import com.v.vsocial.api.Auth
import com.v.vsocial.models.User
import com.v.vsocial.utils.ResponseState
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserProfileRepository @Inject constructor (
    var api: Api,
    @ApplicationContext var context:Context
    ) {

    suspend fun getCurrentUser(username:String="", password:String=""):ResponseState<User>{
        if (username != "" && password != ""){
            Auth.setUserCredentials(context, username, password)
        }
        try {
            val user = api.getCurrentUser(Auth.getUserCredentials(context))
            if (user.code()==401){
                Auth.removeUserCredentials(context)
                return ResponseState.AuthError()
            }
            if(user.code()==200){
                return ResponseState.Success(user.body()!!)
            }
            return ResponseState.UnknownProblem()
        }catch (t:Throwable){
            return ResponseState.NetError()
        }


    }
}