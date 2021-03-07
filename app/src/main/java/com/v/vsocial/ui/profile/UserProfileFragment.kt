package com.v.vsocial.ui.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.v.vsocial.databinding.FragmentMainUserBinding
import com.v.vsocial.api.Auth
import com.v.vsocial.ui.login.LoginActivity
import com.v.vsocial.utils.ActionVM
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class UserProfileFragment : Fragment() {
    val vm: UserProfileVM by lazy{
        ViewModelProvider(this).get(UserProfileVM::class.java)
    }
    lateinit var binding:FragmentMainUserBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding =FragmentMainUserBinding.inflate(inflater,container,false)
        binding.setLifecycleOwner(this)
        binding.viewmodel =vm

        binding.rv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)


        lifecycleScope.launch {
            vm.actions.collect {
                when(it){
                    ActionVM.logout -> {
                        Auth.removeUserCredentials(requireContext())
                        startActivity(Intent(requireContext(), LoginActivity::class.java))
                    }
//                    is ActionVM.showLoadingBar -> binding.progressbar.visibility= View.VISIBLE
//                    is ActionVM.hideLoadingBar -> binding.progressbar.visibility= View.INVISIBLE
                    is ActionVM.showMessage-> Toast.makeText(requireContext(), "${it.msg}", Toast.LENGTH_SHORT).show()
                }
            }
        }
        return binding.root
    }


    }


