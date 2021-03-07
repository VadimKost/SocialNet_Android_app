package com.v.vsocial.ui.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast

import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope

import com.v.vsocial.R
import com.v.vsocial.api.Auth
import com.v.vsocial.databinding.ActivityLoginBinding
import com.v.vsocial.ui.MainActivity
import com.v.vsocial.utils.ActionVM
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    init {
        lifecycleScope.launchWhenCreated {
            vm.actions.collect {
                when(it){
                    is ActionVM.showMessage -> Toast.makeText(this@LoginActivity, "${it.msg}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    lateinit var binding:ActivityLoginBinding

    val vm: LoginVM by lazy{
        ViewModelProvider(this).get(LoginVM::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Auth.getUserCredentials(this)!=null) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
        binding=DataBindingUtil.setContentView(this,R.layout.activity_login)

    }

    fun next(view: View) {
        val username = binding.usernameF.editText?.text.toString()
        val password = binding.passwordF.editText?.text.toString()
        lifecycleScope.launch{
            if (vm.userExist(username, password)){
                startActivity(Intent(this@LoginActivity, MainActivity::class.java))
            }
            else{
                Toast.makeText(this@LoginActivity, "Wrong Username/Password", Toast.LENGTH_SHORT).show()
            }
        }

    }

    fun register(view: View) {
//        startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))

    }


}



