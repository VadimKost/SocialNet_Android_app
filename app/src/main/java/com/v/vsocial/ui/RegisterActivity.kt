package com.v.vsocial.ui

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import com.v.vsocial.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_register_step2.*

@AndroidEntryPoint
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

        val adapter = ArrayAdapter.createFromResource(this, R.array.gender, android.R.layout.simple_list_item_1)
        (gender_f.editText as? AutoCompleteTextView)?.setAdapter(adapter)
    }
//    fun next(view: View) {
//        if(valid(view)){
//            step+=1
//            bind(view)
//        }
//
//    }
//
//    fun back(view: View) {
//        step-=1
//        bind(view)
//    }
//
//    fun bind(view: View){
//        when(step){
//            -1->{
//                startActivity(Intent(this, LoginActivity::class.java))
//            }
//
//             0->{
//                 constraintLayout2.visibility=View.INVISIBLE
//             }
//
//             1->{
//                 birthday_f.requestFocus()
//                 constraintLayout2.visibility=View.VISIBLE
//             }
//
//             2->{
//                 val profile=User_profile(data =birthday_f.editText?.text.toString(),
//                                    aboutMe = about_f.editText?.text.toString(),
//                                    adress = address_f.editText?.text.toString())
//
//                 val user=User(user_profile = profile,username = username_f.editText?.text.toString(),email = email_f.editText?.text.toString(),password = password_f.editText?.text.toString())
//                 SocialNetService.Api().createUser(user).enqueue(object : Callback<User> {
//
//                 override fun onFailure(call: Call<User>, t: Throwable) {
//                    TODO("Not yet implemented")
//                 }
//
//                 override fun onResponse(call: Call<User>, response: Response<User>) {
//                    if (response.code()==201){
//                        Auth.setUserCredentials(this@RegisterActivity, username_f.editText?.text.toString(),password_f.editText?.text.toString())
//                        startActivity(Intent(this@RegisterActivity,MainActivity::class.java))
//                    }
//                 }
//                })
//
//             }
//        }
//    }






}