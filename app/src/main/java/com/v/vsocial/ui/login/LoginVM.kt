package com.v.vsocial.ui.login

import androidx.lifecycle.ViewModel
import com.v.vsocial.api.auth.Auth
import com.v.vsocial.usecases.getuser.GetCurrentUserUseCaseImpl
import com.v.vsocial.utils.ActionVM
import com.v.vsocial.utils.ResponseState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

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

    var userExists= runBlocking {userExist()}

    private val _actions: MutableStateFlow<ActionVM> = MutableStateFlow(ActionVM.waitingAction)
    val actions: StateFlow<ActionVM> = _actions

   suspend fun userExist(username:String?=null, password:String?=null):Boolean?{
       when(getCurrentUserUseCaseImpl(username, password)){
            is ResponseState.Success -> return true
            is ResponseState.AuthError ->  return false
            is ResponseState.NetError -> {
                _actions.value = ActionVM.showMessage("NO INTERNET CONNECTION")
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
