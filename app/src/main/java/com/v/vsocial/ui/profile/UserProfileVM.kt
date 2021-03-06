package com.v.vsocial.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.v.vsocial.api.auth.Auth
import com.v.vsocial.models.User
import com.v.vsocial.usecases.getuser.GetCurrentUserUseCaseImpl
import com.v.vsocial.utils.ActionVM
import com.v.vsocial.utils.ResponseState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class UserProfileVM @Inject constructor(
    var getCurrentUserUseCaseImpl: GetCurrentUserUseCaseImpl,
    var auth: Auth
): ViewModel() {

    private val _actions: MutableStateFlow<ActionVM> = MutableStateFlow(ActionVM.waitingAction)
    val actions: StateFlow<ActionVM> = _actions

    private val _user: MutableStateFlow<User?> = MutableStateFlow(null)
    val user: StateFlow<User?> = _user

    init{
        viewModelScope.launch {
            _actions.value=ActionVM.showLoadingBar
            when(val userstate = getCurrentUserUseCaseImpl()){
                is ResponseState.Success -> _user.value = userstate.data
                is ResponseState.AuthError -> _actions.value =ActionVM.logout
                is ResponseState.Offline -> {
                    _actions.value =ActionVM.showMessage("Offline")
                    _user.value=userstate.data
                }
            }
            _actions.value=ActionVM.hideLoadingBar
        }
    }

    fun logout(){
        auth.removeUserCredentials()
    }


}




