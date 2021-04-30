package com.v.vsocial.usecases.getuser

import com.v.vsocial.api.auth.Auth
import com.v.vsocial.models.User
import com.v.vsocial.repository.UserProfileRepository
import com.v.vsocial.utils.ResponseState
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetCurrentUserUseCaseImpl @Inject constructor(
    val userProfileRepository: UserProfileRepository,
    val Auth: Auth
) : GetCurrentUserUseCase {

    override suspend fun invoke(username: String?, password: String?): ResponseState<User> {
        if (username != null && password != null){
            Auth.setUserCredentials(username,password)
        }
        val user = userProfileRepository.getCurrentUser()
        return user
    }
}


