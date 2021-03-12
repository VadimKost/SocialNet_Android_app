package com.v.vsocial.ui.login

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.v.vsocial.R
import com.v.vsocial.api.Auth
import com.v.vsocial.databinding.FragmentLoginBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import android.view.animation.AlphaAnimation
import androidx.core.widget.addTextChangedListener
import com.v.vsocial.ui.MainActivity


@AndroidEntryPoint
class LoginFragment : Fragment() {
    lateinit var binding: FragmentLoginBinding

    val vm: LoginVM by lazy {
        ViewModelProvider(this).get(LoginVM::class.java)
    }
    var itWasValid=false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Auth.getUserCredentials(requireContext()) != null) {
            findNavController().popBackStack()
            findNavController().navigate(R.id.action_global_userProfileFragment)
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        binding.vm=vm
        binding.setLifecycleOwner(this)

        var activity=activity as MainActivity
        activity.activityBinding.navigationView.visibility=View.GONE
        activity.activityBinding.appbar.visibility=View.GONE

        onClickButtonLogin()
        initFieldListeners()
        dataIsValid()
        return binding.root
    }

    fun onClickButtonLogin() {
        binding.login.setOnClickListener {
            val username = binding.usernameF.editText?.text.toString()
            val password = binding.passwordF.editText?.text.toString()
            lifecycleScope.launch {
                if (vm.userExist(username, password)) {
                    findNavController().popBackStack()
                    findNavController().navigate(R.id.action_global_userProfileFragment)
                } else {
                    Toast.makeText(
                        requireContext(),
                        "Wrong Username/Password",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
    fun initFieldListeners(){
        binding.usernameF.editText?.addTextChangedListener {
            vm.username.value=it.toString()
        }
        binding.passwordF.editText?.addTextChangedListener {
            vm.password.value=it.toString()
        }
    }

    fun dataIsValid(){
        val animation1 = AlphaAnimation(0f, 1.0f).apply {
            duration = 2000
        }

        lifecycleScope.launch{
            vm.isValid.collect {
                Log.e("a",itWasValid.toString())
                if (it){
                    if(!itWasValid){
                        binding.login.visibility=View.VISIBLE
                        binding.login.startAnimation(animation1)
                        itWasValid=true
                    }
                }else{
                    itWasValid=false
                    binding.login.visibility=View.INVISIBLE
                }
                }
            }
        }
    }



