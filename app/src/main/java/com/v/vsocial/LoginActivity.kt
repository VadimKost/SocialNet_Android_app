package com.v.vsocial

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.v.vsocial.network.Auth
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.Credentials

class LoginActivity : AppCompatActivity() {

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

        lifecycleScope.launch(Dispatchers.IO) {
            val response = SocialNet.Api().getCurrentUser(Credentials.basic(username, password))
            withContext(Dispatchers.Main){
            if (response.isSuccessful) {
                Auth.setUserCredentials(this@LoginActivity, username, password)
                startActivity(Intent(this@LoginActivity, MainActivity::class.java))
            }else{
                Toast.makeText(this@LoginActivity, "Incorrect username/password", Toast.LENGTH_SHORT).show()
            }
        }}

    }

    fun register(view: View) {
        startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))

    }


}



