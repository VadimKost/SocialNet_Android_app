package com.v.vsocial.ui.registration

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.v.vsocial.R
import com.v.vsocial.databinding.FragmentMainUserBinding
import com.v.vsocial.databinding.FragmentRegistrationStep1Binding
import com.v.vsocial.databinding.FragmentRegistrationStep2Binding
import com.v.vsocial.ui.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegistrationStep2Fragment : Fragment() {
    lateinit var binding: FragmentRegistrationStep2Binding
    val vm: RegistrationVM by lazy{
        ViewModelProvider(requireActivity()).get(RegistrationVM::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentRegistrationStep2Binding.inflate(inflater,container,false)
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
        binding.nextbutton.setOnClickListener {
            Toast.makeText(activity, "DONE", Toast.LENGTH_SHORT).show()
        }
    }
    fun onClickBackButton(){
        binding.backbutton.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}