package com.v.vsocial.ui.login

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.v.vsocial.api.Auth
import com.v.vsocial.repository.UserProfileRepository
import com.v.vsocial.usecases.getuser.GetCurrentUserUseCase
import com.v.vsocial.usecases.getuser.GetCurrentUserUseCaseImpl
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
    val getCurrentUserUseCaseImpl: GetCurrentUserUseCaseImpl,
    val auth: Auth
): ViewModel() {
    var username=MutableStateFlow("")
    var password=MutableStateFlow("")
    
    var itWasValid=false
    
    val buttonEnabled: Flow<Boolean?> = combine(username, password) { username, password->
        if (validate(username, password)){
            if(!itWasValid){
                itWasValid=true
                return@combine true
            }
        }else{
            itWasValid=false
            return@combine false
        }
        return@combine null

    }

    private val _actions: MutableStateFlow<ActionVM> = MutableStateFlow(ActionVM.waitingAction)
    val actions: StateFlow<ActionVM> = _actions

   suspend fun userExist(username:String?=null, password:String?=null):Boolean?{
       when(getCurrentUserUseCaseImpl(username, password)){
            is ResponseState.Success -> return true
            is ResponseState.AuthError ->  return false
            is ResponseState.NetError -> {
                _actions.value = ActionVM.showMessage("NO INTERNET CONNECTION")
                if(auth.getUserCredentials()!= null){
                    return true
                }
                return null
            }
            else -> return null
        }
        }

    fun validate(username: String,password: String):Boolean{
        if (username !="" && password !=""){
            return true
        }else{
            return false
        }
    }

    }
