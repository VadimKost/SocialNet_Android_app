package com.v.vsocial

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.v.vsocial.adapters.LinksAdapter
import com.v.vsocial.databinding.FragmentMainUserBinding
import com.v.vsocial.network.Auth
import com.v.vsocial.viewmodels.Action
import com.v.vsocial.viewmodels.MainActivityViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


class UserFragment : Fragment() {
    val model: MainActivityViewModel by lazy{
        ViewModelProvider(this).get(MainActivityViewModel::class.java)
    }
    lateinit var binding:FragmentMainUserBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding =FragmentMainUserBinding.inflate(inflater,container,false)
        binding.rv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        lifecycleScope.launch {
            model.user.collect {
                binding.user = it
            }
        }

        lifecycleScope.launch {
            model.actions.collect {
                when(it){
                    Action.logout -> {
                        Auth.removeUserCredentials(requireContext())
                        startActivity(Intent(requireContext(), LoginActivity::class.java))
                    }
                    Action.showLoadingBar -> binding.progressbar.visibility= View.VISIBLE
                    Action.hideLoadingBar -> binding.progressbar.visibility= View.INVISIBLE
                }
            }
        }
        return binding.root
    }


    }


