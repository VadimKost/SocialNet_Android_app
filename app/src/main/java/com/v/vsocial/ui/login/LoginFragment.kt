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
import com.v.vsocial.api.Auth
import com.v.vsocial.databinding.FragmentLoginBinding
import com.v.vsocial.ui.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : Fragment() {
    lateinit var binding: FragmentLoginBinding

    val vm: LoginVM by lazy {
        ViewModelProvider(this).get(LoginVM::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Auth.getUserCredentials(requireContext()) != null) {
            findNavController().popBackStack()
            findNavController().navigate(R.id.userProfileFragment)
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val activity=activity as MainActivity
        activity.activityBinding.appbar.visibility=View.GONE
        activity.activityBinding.navigationView.visibility=View.GONE
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        binding.setLifecycleOwner(this)
        onClickButtonLogin()
        return binding.root
    }

    fun onClickButtonLogin() {
        binding.login.setOnClickListener {
            val username = binding.usernameF.editText?.text.toString()
            val password = binding.passwordF.editText?.text.toString()
            lifecycleScope.launch {
                if (vm.userExist(username, password)) {
                    findNavController().navigate(R.id.userProfileFragment)
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


}
