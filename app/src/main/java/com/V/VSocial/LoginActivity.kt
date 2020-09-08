package com.V.VSocial

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
        setContentView(R.layout.activity_login)

    }

    fun next(view: View) {
        val username =username_f.editText?.text.toString()
        val password =password_f.editText?.text.toString()
        SocialService.Api()
            .getCurrentUser(Credentials.basic(username,password))
            .enqueue(object : Callback<User> {
                override fun onResponse(call: Call<User>, response: Response<User>) {
                    val post: User? = response.body()
                    if (response.isSuccessful){
                        
                    }
                }
                override fun onFailure(call: Call<User?>, t: Throwable) {
                    Log.e("","lox")
                    t.printStackTrace()
                }
            })
    }


}



