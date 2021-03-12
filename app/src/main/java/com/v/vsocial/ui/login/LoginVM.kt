package com.v.vsocial.ui.login

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.v.vsocial.api.Auth
import com.v.vsocial.repository.UserProfileRepository
import com.v.vsocial.utils.ActionVM
import com.v.vsocial.utils.ResponseState
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@HiltViewModel
class LoginVM @Inject constructor(
    val userProfileRepository: UserProfileRepository,
): ViewModel() {
    var username=MutableStateFlow("")
    var password=MutableStateFlow("")
    val isValid: Flow<Boolean> = combine(username, password) { username, password->
        return@combine validate(username, password)
    }
    init {
        viewModelScope.launch {
        }
    }

    private val _actions: MutableStateFlow<ActionVM> = MutableStateFlow(ActionVM.waitingAction)
    val actions: StateFlow<ActionVM> = _actions

   suspend fun userExist(username:String, password:String):Boolean{
       var exist =false
       when(userProfileRepository.getCurrentUser(username, password)){
            is ResponseState.Success -> exist=true
            is ResponseState.AuthError ->  exist=false
            is ResponseState.NetError -> _actions.value =ActionVM.showMessage("NO INTERNET CONNECTION")
        }
       return exist
        }

    fun validate(username: String,password: String):Boolean{
        if (username !="" && password !=""){
            return true
        }else{
            return false
        }
    }

    }
