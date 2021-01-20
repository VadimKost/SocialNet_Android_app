package com.v.vsocial.viewmodels

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.v.vsocial.SocialNet
import com.v.vsocial.models.User
import com.v.vsocial.models.UserProfile
import com.v.vsocial.network.Auth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivityViewModel(application: Application): AndroidViewModel(application) {
    val context: Context = getApplication()

    private val _userFlow = MutableStateFlow(User(user_profile = UserProfile()))
    val userFlow: StateFlow<User> = _userFlow
//
//    private val _cFlow = MutableStateFlow(0)
//    val cFlow: StateFlow<Int> = _cFlow



    fun getUser(){
        viewModelScope.launch(Dispatchers.IO){
            val user = SocialNet.Api().getCurrentUser(Auth.getUserCredentials(context))
            val contats= SocialNet.Api().getContactAndLinks(Auth.getUserCredentials(context))
            if (user.code()==401){
                Auth.removeUserCredentials(context)
//                startActivity(Intent(this@MainActivity, LoginActivity::class.java))
            }
            if(user.code()==200){
                withContext(Dispatchers.Main){
                    _userFlow.value = user.body()!!
                }
            }

        }
    }
//    fun increment(){
//
//        _cFlow.value =_cFlow.value+1
//
//    }

}