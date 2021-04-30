package com.v.vsocial.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.v.vsocial.R
import com.v.vsocial.databinding.FragmentLoginBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import android.view.animation.AlphaAnimation
import androidx.core.widget.addTextChangedListener
import com.v.vsocial.ui.MainActivity
import com.v.vsocial.utils.ActionVM
import kotlinx.coroutines.runBlocking


@AndroidEntryPoint
class LoginFragment : Fragment() {
    lateinit var binding: FragmentLoginBinding

    val vm: LoginVM by lazy {
        ViewModelProvider(this).get(LoginVM::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        runBlocking { vm.userExistenceCheck() }
        if (vm.userExist.value) {
            findNavController().popBackStack()
            findNavController().navigate(R.id.userProfile)
        }
        lifecycleScope.launch {
            vm.actions.collect {
                when(it){
                    is ActionVM.showMessage-> Toast.makeText(requireContext(), "${it.msg}", Toast.LENGTH_SHORT).show()
                }
            }
        }
        lifecycleScope.launch {
            vm.userExist.collect{
                if (it){
                    findNavController().popBackStack()
                    findNavController().navigate(R.id.userProfile)
                }
            }
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
        onClickButtonRegistration()
        initFieldListeners()
        dataIsValid()
        return binding.root
    }

    fun onClickButtonLogin() {
        binding.login.setOnClickListener {
            val username = binding.usernameF.editText?.text.toString()
            val password = binding.passwordF.editText?.text.toString()
            lifecycleScope.launch {
                vm.userExistenceCheck(username, password)
            }
        }
    }

    fun onClickButtonRegistration(){
        binding.register.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registrationStep1Fragment)
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
            vm.buttonEnabled.collect {
                if (it == true){
                    binding.login.visibility=View.VISIBLE
                    binding.login.startAnimation(animation1)
                }else if (it ==false){
                    binding.login.visibility=View.INVISIBLE
                }
            }
        }
    }



}



