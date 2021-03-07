package com.v.vsocial.ui.login

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.v.vsocial.api.Auth
import com.v.vsocial.repository.UserProfileRepository
import com.v.vsocial.utils.ActionVM
import com.v.vsocial.utils.ResponseState
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginVM @Inject constructor(
    @ApplicationContext val context: Context,
    val userProfileRepository: UserProfileRepository,
): ViewModel() {
    private val _actions: MutableStateFlow<ActionVM> = MutableStateFlow(ActionVM.waitingAction)
    val actions: StateFlow<ActionVM> = _actions

   suspend fun userExist(username:String, password:String):Boolean{
       var exist =false
       Auth.setUserCredentials(context, username, password)
       when(userProfileRepository.getCurrentUser()){
            is ResponseState.Success -> exist=true
            is ResponseState.AuthError -> {
                exist=false
                Auth.removeUserCredentials(context)
            }
            is ResponseState.NetError -> _actions.value =ActionVM.showMessage("NO INTERNET CONNECTION")
        }
       return exist
        }

    }
