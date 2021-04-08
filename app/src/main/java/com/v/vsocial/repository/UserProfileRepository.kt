package com.v.vsocial.repository

import android.content.Context
import com.v.vsocial.Api
import com.v.vsocial.models.User
import com.v.vsocial.models.UserProfile
import com.v.vsocial.utils.ResponseState
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserProfileRepository @Inject constructor (
    var api: Api,
    @ApplicationContext var context:Context
    ) {

    suspend fun getCurrentUser(): ResponseState<User> {
        val user = api.getCurrentUser()
        when (user) {
            is ResponseState.NetError -> {
//                fakedata
                val user_profile=UserProfile(2,"off_geo","off","",1,"https://www.biletik.aero/upload/resize_cache/format_converted/9009fd7ec08c448bd94f273096b40e2b.webp")
                val _user=User(2,user_profile,"vk","v","k","cv")
                user.data=_user
            }
        }
        return user
    }
}
