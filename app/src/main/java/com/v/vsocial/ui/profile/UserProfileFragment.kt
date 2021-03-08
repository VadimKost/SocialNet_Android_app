package com.v.vsocial.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.v.vsocial.R
import com.v.vsocial.api.Auth
import com.v.vsocial.ui.MainActivity
import com.v.vsocial.utils.ActionVM
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import com.v.vsocial.databinding.FragmentMainUserBinding

@AndroidEntryPoint
class UserProfileFragment : Fragment() {
    val vm: UserProfileVM by lazy{
        ViewModelProvider(this).get(UserProfileVM::class.java)
    }
    lateinit var binding:FragmentMainUserBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        var activity=activity as MainActivity
        activity.activityBinding.appbar.visibility=View.VISIBLE
        activity.activityBinding.navigationView.visibility=View.VISIBLE
        binding =FragmentMainUserBinding.inflate(inflater,container,false)
        binding.setLifecycleOwner(this)
        binding.viewmodel =vm

        binding.rv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        binding.logout.setOnClickListener{
            Auth.removeUserCredentials(requireContext())
            findNavController().popBackStack()
            findNavController().navigate(R.id.logout)
        }

        lifecycleScope.launch {
            vm.actions.collect {
                when(it){
                    ActionVM.logout -> {
                        findNavController().popBackStack()
                        findNavController().navigate(R.id.logout)
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


