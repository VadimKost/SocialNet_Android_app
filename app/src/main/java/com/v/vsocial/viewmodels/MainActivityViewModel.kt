package com.v.vsocial.viewmodels

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.v.vsocial.SocialNet
import com.v.vsocial.models.ContactsLinks
import com.v.vsocial.models.User
import com.v.vsocial.models.UserProfile
import com.v.vsocial.network.Auth
import com.v.vsocial.utils.Action
import com.v.vsocial.utils.State
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MainActivityViewModel(application: Application): AndroidViewModel(application) {
    val coroutineExceptionHanlder = CoroutineExceptionHandler{_, throwable ->
        _stateFlow.value=State.networkError
    }
    val context: Context = getApplication()

    private val _actionFlow: MutableStateFlow<Action> = MutableStateFlow(Action.waitingAction)
    val actionFlow: StateFlow<Action> = _actionFlow

    private val _stateFlow: MutableStateFlow<State> = MutableStateFlow(State.loading)
    val stateFlow: StateFlow<State> = _stateFlow

    init{
        getUser()
        getContacts()
    }

    fun getUser(){
            viewModelScope.launch(Dispatchers.IO+coroutineExceptionHanlder){
                val user = SocialNet.Api().getCurrentUser(Auth.getUserCredentials(context))
                if (user.code()==401){
                    _actionFlow.value=Action.logout
                }
                if(user.code()==200){
                    withContext(Dispatchers.Main){
                        _stateFlow.value = State.success(user.body()!!,"user")
                    }
                }

            }
    }
    fun getContacts(){
        viewModelScope.launch(Dispatchers.IO+coroutineExceptionHanlder){
            val contacts= SocialNet.Api().getContactAndLinks(Auth.getUserCredentials(context))
            if (contacts.code()==401){
               _actionFlow.value=Action.logout
            }
            if(contacts.code()==200){
                withContext(Dispatchers.Main){
                    _stateFlow.value = State.success(contacts.body()!!,"contacts")
                }
            }

        }
    }


}