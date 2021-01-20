package com.v.vsocial

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.v.vsocial.databinding.FragmentMainUserBinding
import com.v.vsocial.viewmodels.MainActivityViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


class UserFragment : Fragment() {
    val model: MainActivityViewModel by lazy { ViewModelProvider(requireActivity()).get(MainActivityViewModel::class.java) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        model.getUser()
        val binding =FragmentMainUserBinding.inflate(inflater,container,false)
        lifecycleScope.launch {
            model.userFlow.collect {
                binding.user=it
                Log.e("NNNN","${it}")}
        }
//        lifecycleScope.launch {
//            model.cFlow.collect{
//                binding.count=it
//                Log.e("NNNN","${it}")
//            }
//        }
//        binding.textView11.setOnClickListener { model.increment() }

        return binding.root
    }

}
