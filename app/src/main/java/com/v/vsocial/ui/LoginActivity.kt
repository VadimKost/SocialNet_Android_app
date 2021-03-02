package com.v.vsocial.ui

import android.content.Intent
import android.os.Bundle
import android.view.View

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider

import com.v.vsocial.R
import com.v.vsocial.api.Auth
import com.v.vsocial.viewmodels.LoginVM
import com.v.vsocial.viewmodels.UserProfileVM
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_login.*

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    val vm: LoginVM by lazy{
        ViewModelProvider(this).get(LoginVM::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Auth.getUserCredentials(this)!=null) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
        setContentView(R.layout.activity_login)

    }

    fun next(view: View) {
        val username = username_f.editText?.text.toString()
        val password = password_f.editText?.text.toString()
//        lifecycleScope.launch(Dispatchers.IO) {
//            val response = SocialNet.Api().getCurrentUser(Credentials.basic(username, password))
//            withContext(Dispatchers.Main){
//            if (response.isSuccessful) {
//                Auth.setUserCredentials(this@LoginActivity, username, password)
//                startActivity(Intent(this@LoginActivity, MainActivity::class.java))
//            }else{
//                Toast.makeText(this@LoginActivity, "Incorrect username/password", Toast.LENGTH_SHORT).show()
//            }
//        }}

    }

    fun register(view: View) {
        startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))

    }


}



