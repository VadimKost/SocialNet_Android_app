package com.v.vsocial.ui.login

import androidx.lifecycle.ViewModel
import com.v.vsocial.api.auth.Auth
import com.v.vsocial.usecases.getuser.GetCurrentUserUseCaseImpl
import com.v.vsocial.utils.ActionVM
import com.v.vsocial.utils.ResponseState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class LoginVM @Inject constructor(
    val getCurrentUserUseCaseImpl: GetCurrentUserUseCaseImpl,
    val auth: Auth
): ViewModel() {
    val username=MutableStateFlow("")
    val password=MutableStateFlow("")
    
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

    private val _userExist= MutableStateFlow(false)
    val userExist:StateFlow<Boolean> = _userExist

    private val _actions: MutableStateFlow<ActionVM> = MutableStateFlow(ActionVM.waitingAction)
    val actions: StateFlow<ActionVM> = _actions

   suspend fun userExistenceCheck(username:String?=null, password:String?=null){
       when(getCurrentUserUseCaseImpl(username, password)){
            is ResponseState.Success -> _userExist.value=true
            is ResponseState.AuthError ->  {
                _actions.value = ActionVM.showMessage("Wrong Username/Password")
                _userExist.value=false
            }
            is ResponseState.Offline -> {
                _actions.value = ActionVM.showMessage("NO INTERNET CONNECTION")
                _userExist.value=false
            }
            else -> _userExist.value=false
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
