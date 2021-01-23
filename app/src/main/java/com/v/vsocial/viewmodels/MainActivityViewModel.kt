package com.v.vsocial.viewmodels

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.v.vsocial.SocialNet
import com.v.vsocial.models.ContactsLinks
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

    private val _contactsFlow: MutableStateFlow<List<ContactsLinks>?> = MutableStateFlow(null)
    val contactsLinksFlow: StateFlow<List<ContactsLinks>?> = _contactsFlow

    init{
        getContacts()
        getUser()
    }


    fun getUser(){
        viewModelScope.launch(Dispatchers.IO){
            val user = SocialNet.Api().getCurrentUser(Auth.getUserCredentials(context))
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
    fun getContacts(){
        viewModelScope.launch(Dispatchers.IO){
            val contacts= SocialNet.Api().getContactAndLinks(Auth.getUserCredentials(context))
            if (contacts.code()==401){
                Auth.removeUserCredentials(context)
//                startActivity(Intent(this@MainActivity, LoginActivity::class.java))
            }
            if(contacts.code()==200){
                withContext(Dispatchers.Main){
                    _contactsFlow.value = contacts.body()
                }
            }

        }
    }


}