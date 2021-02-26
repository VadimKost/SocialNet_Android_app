package com.v.vsocial.viewmodels

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.v.vsocial.models.User
import com.v.vsocial.repository.UserProfileRepository
import com.v.vsocial.utils.ActionVM
import com.v.vsocial.utils.ResponseState
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class UserProfileVM @Inject constructor(
    @ApplicationContext context: Context,
    userProfileRepository: UserProfileRepository
): ViewModel() {

    val coroutineExceptionHanlder = CoroutineExceptionHandler{_, throwable ->
//        _stateFlow.value=State.networkError
    }




    private val _actions: MutableStateFlow<ActionVM> = MutableStateFlow(ActionVM.waitingAction)
    val actions: StateFlow<ActionVM> = _actions

    private val _user: MutableStateFlow<User?> = MutableStateFlow(null)
    val user: StateFlow<User?> = _user

    init{
        viewModelScope.launch {
            _actions.value=ActionVM.showLoadingBar
            when(val userstate = userProfileRepository.getCurrentUser()){
                is ResponseState.Success -> _user.value = userstate.data
                is ResponseState.AuthError -> _actions.value =ActionVM.logout
            }
            _actions.value=ActionVM.hideLoadingBar
        }


    }

//    suspend fun getUser():ResponseState<User>{
//                val user = SocialNet.Api().getCurrentUser(Auth.getUserCredentials(context))
//                if (user.code()==401){
//                    return ResponseState.AuthError()
//                }
//                if(user.code()==200){
//                    return ResponseState.Success(user.body()!!)
//                }
//                return ResponseState.UnknownProblem()
//
//    }
//    fun getContacts(){
//        viewModelScope.launch(Dispatchers.IO+coroutineExceptionHanlder){
//            val contacts= SocialNet.Api().getContactAndLinks(Auth.getUserCredentials(context))
//            if (contacts.code()==401){
//               _actionFlow.value=Action.logout
//            }
//            if(contacts.code()==200){
//                withContext(Dispatchers.Main){
//                    _stateFlow.value = State.success.contactsSuccess(contacts.body()!!)
//                }
//            }
//
//        }
//    }


}




