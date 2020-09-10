package com.V.VSocial

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_user.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class UserActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)
        setSupportActionBar(toolbar as Toolbar)

        SocialService.Api()
            .getCurrentUser(getSharedPreferences("Settings",Context.MODE_PRIVATE).getString("UAC",""))
            .enqueue(object : Callback<User> {

                override fun onResponse(call: Call<User>, response: Response<User>) {
                    val user: User? = response.body()
                    if (response.isSuccessful){
                        if (user != null) {
                            username_f.text=user.username
                            val mPicasso = Picasso.with(this@UserActivity)
                            mPicasso.setIndicatorsEnabled(true)
                            mPicasso.load(user.img.photo).centerCrop().fit().into(image)
                            //may remake
                            card.removeView(spiner)
                            main.visibility=View.VISIBLE
                        }
                    }
                    if (response.code() == 401){
                            getSharedPreferences("Settings",Context.MODE_PRIVATE).edit().remove("UAC").apply()
                            startActivity(Intent(this@UserActivity,LoginActivity::class.java))
                        }
                    else{
                        Toast.makeText(this@UserActivity, "Something went WRONG", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<User?>, t: Throwable) {
                    Log.e("","lox")
                    t.printStackTrace()
                }
            })


    }




}