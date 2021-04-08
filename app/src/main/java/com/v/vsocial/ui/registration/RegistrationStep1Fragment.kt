package com.v.vsocial.ui.registration

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.v.vsocial.R
import com.v.vsocial.databinding.FragmentRegistrationStep1Binding
import com.v.vsocial.ui.MainActivity
import com.v.vsocial.ui.profile.UserProfileVM
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegistrationStep1Fragment : Fragment() {
    lateinit var binding: FragmentRegistrationStep1Binding
    val vm: RegistrationVM by lazy{
        ViewModelProvider(requireActivity()).get(RegistrationVM::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding= FragmentRegistrationStep1Binding.inflate(inflater,container,false)
        binding.setLifecycleOwner(this)
        binding.viewmodel =vm

        var activity=activity as MainActivity
        activity.activityBinding.navigationView.visibility=View.GONE
        activity.activityBinding.appbar.visibility=View.GONE

        onClickBackButton()
        onClickNextButton()

        return binding.root
    }

    fun onClickNextButton(){
        binding.next.setOnClickListener{
            findNavController()
                .navigate(R.id.action_registrationStep1Fragment_to_registrationStep2Fragment)

        }
    }
    fun onClickBackButton(){
        binding.back.setOnClickListener {
            findNavController().popBackStack()
        }
    }


}