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
                        Toast.makeText(this@LoginActivity, "Something went WRONG", Toast.LENGTH_SHORT).show()
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
//        val p=User_profile(gender = 1,data = "2020-10-16")
//        val l=User(user_profile = p,username = "Varil",email = "Vari@kasi.ru",password = "12")
//        SocialNetService.Api().createUser(l)
//            .enqueue(object : Callback<User> {
//                override fun onFailure(call: Call<User>, t: Throwable) {
//                    TODO("Not yet implemented")
//                }
//
//                override fun onResponse(
//                    call: Call<User>,
//                    response: Response<User>
//                ) {
//                    Toast.makeText(this@LoginActivity, "${response.code()}", Toast.LENGTH_SHORT).show()
//                    Log.e("Error code 400", response.errorBody()?.string().toString());
//                }
//
//            })
        startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))

    }


}



