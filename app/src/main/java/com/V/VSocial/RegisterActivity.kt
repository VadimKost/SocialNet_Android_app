package com.V.VSocial

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.activity_register.password_f
import kotlinx.android.synthetic.main.activity_register.username_f
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity : AppCompatActivity() {
    var step=0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        //set dataPickerListener
        birthday_f.editText?.setOnClickListener {
            val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                val data ="${year}-${monthOfYear+1}-${dayOfMonth}"
                birthday_f.editText?.setText(data)
            }, 2000, 0, 1)
            dpd.show()
        }
        //
    }
    fun next(view: View) {
        if(valid(view)){
            step+=1
            bind(view)
        }

    }

    fun back(view: View) {
        step-=1
        bind(view)
    }

    fun bind(view: View){
        when(step){
            -1->{
                startActivity(Intent(this, LoginActivity::class.java))
            }

             0->{
                 constraintLayout.visibility=View.VISIBLE
                 constraintLayout2.visibility=View.INVISIBLE
             }

             1->{
                 birthday_f.requestFocus()
                 constraintLayout.visibility=View.INVISIBLE
                 constraintLayout2.visibility=View.VISIBLE
             }

             2->{
                 val profile=User_profile(data =birthday_f.editText?.text.toString(),
                                    aboutMe = about_f.editText?.text.toString(),
                                    adress = address_f.editText?.text.toString())

                 val user=User(user_profile = profile,username = username_f.editText?.text.toString(),email = email_f.editText?.text.toString(),password = password_f.editText?.text.toString())
                 SocialNetService.Api().createUser(user).enqueue(object : Callback<User> {

                 override fun onFailure(call: Call<User>, t: Throwable) {
                    TODO("Not yet implemented")
                 }

                 override fun onResponse(call: Call<User>, response: Response<User>) {
                    if (response.code()==201){
                        setUserCredentials(this@RegisterActivity, username_f.editText?.text.toString(),password_f.editText?.text.toString())
                        startActivity(Intent(this@RegisterActivity,MainActivity::class.java))
                    }
                 }
                })

             }
        }
    }

    fun valid(view: View):Boolean{
        var valid=true
        when(step){
            0->{
                username_f.error=null
                password_f.error=null
                email_f.error=null
                email_f.error= null
                if (username_f.editText?.text.toString()==""){
                    username_f.error="It should be filled"
                    valid=false
                }
                if (password_f.editText?.text.toString()==""){
                    password_f.error="It should be filled"
                    valid=false
                }
                if (email_f.editText?.text.toString()==""){
                    email_f.error="It should be filled"
                    valid=false
                }
                if(!EMAIL_ADDRESS_PATTERN.matcher(email_f.editText?.text.toString()).matches()){
                    email_f.error="It should be correct"
                    valid=false
                }

                return valid

            }

            1->{
                return valid
            }
        }
        return false
    }




}