package com.v.vsocial.viewmodels

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.viewModelScope
import com.v.vsocial.SocialNet
import com.v.vsocial.models.ContactsLinks
import com.v.vsocial.models.User
import com.v.vsocial.models.UserProfile
import com.v.vsocial.network.Auth
import com.v.vsocial.utils.ResponseState
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MainActivityViewModel(application: Application): AndroidViewModel(application) {
    val coroutineExceptionHanlder = CoroutineExceptionHandler{_, throwable ->
//        _stateFlow.value=State.networkError
    }
    val context: Context = getApplication()

    private val _actions: MutableStateFlow<Action> = MutableStateFlow(Action.waitingAction)
    val actions: StateFlow<Action> = _actions

    private val _user: MutableStateFlow<User?> = MutableStateFlow(null)
    val user: StateFlow<User?> = _user

    init{
        _actions.value=Action.showLoadingBar
        viewModelScope.launch {
            var userstate = getUser()
            when(userstate){
                is ResponseState.Success -> _user.value = userstate.data
                is ResponseState.AuthError -> _actions.value =Action.logout
            }
        }
        _actions.value=Action.hideLoadingBar

    }

    suspend fun getUser():ResponseState<User>{
                val user = SocialNet.Api().getCurrentUser(Auth.getUserCredentials(context))
                if (user.code()==401){
                    return ResponseState.AuthError()
                }
                if(user.code()==200){
                    return ResponseState.Success(user.body()!!)
                }
                return ResponseState.UnknownProblem()

    }
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




sealed class Action{
    object logout : Action()
    object showLoadingBar : Action()
    object hideLoadingBar : Action()
    object waitingAction:Action()
}