package com.v.vsocial.usecases.getuser

import com.v.vsocial.models.User
import com.v.vsocial.utils.ResponseState

interface GetCurrentUserUseCase {
    suspend operator fun invoke(username:String?=null,password:String?=null):ResponseState<User>
}