package com.V.VSocial

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_login.*
import okhttp3.Credentials
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (getUserCredentials(this)!=null) {
           startActivity(Intent(this, MainActivity::class.java))
            Log.e("2","${getUserCredentials(this)}")
        }
        setContentView(R.layout.activity_login)

    }

    fun next(view: View) {
        val username = username_f.editText?.text.toString()
        val password = password_f.editText?.text.toString()
        SocialNetService.Api().getCurrentUser(Credentials.basic(username, password))
            .enqueue(object : Callback<User> {

                override fun onResponse(call: Call<User>, response: Response<User>) {
                    val post: User? = response.body()
                    if (response.isSuccessful) {
                        setUserCredentials(this@LoginActivity, username, password)
                        startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                    } else {
                        Toast.makeText(this@LoginActivity, "WRONG", Toast.LENGTH_SHORT).show()
                        Log.e("", response.errorBody()?.string().toString())
                    }
                }

                override fun onFailure(call: Call<User?>, t: Throwable) {
                    Log.e("", "lox")
                    t.printStackTrace()
                }
            })
    }

    fun register(view: View) {
        startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))

    }


}



