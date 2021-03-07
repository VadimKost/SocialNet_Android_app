package com.v.vsocial.repository

import android.content.Context
import com.v.vsocial.UserProfileApi
import com.v.vsocial.api.Auth
import com.v.vsocial.models.User
import com.v.vsocial.utils.ResponseState
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserProfileRepository @Inject constructor (
    var userProfileApi: UserProfileApi,
    @ApplicationContext var context:Context
    ) {

    suspend fun getCurrentUser():ResponseState<User>{
        try {
            val user = userProfileApi.getCurrentUser(Auth.getUserCredentials(context))
            if (user.code()==401){
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