package com.v.vsocial

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.v.vsocial.adapters.LinksAdapter
import com.v.vsocial.databinding.FragmentMainUserBinding
import com.v.vsocial.viewmodels.MainActivityViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


class UserFragment : Fragment() {
    val model: MainActivityViewModel by lazy { ViewModelProvider(requireActivity()).get(MainActivityViewModel::class.java) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val binding =FragmentMainUserBinding.inflate(inflater,container,false)

        binding.rv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)


        lifecycleScope.launch {
            model.userFlow.collect {
                binding.user=it
            }
        }

        lifecycleScope.launch {
            model.contactsLinksFlow.collect {
                binding.rv.adapter=LinksAdapter(it)
            }
        }

        return binding.root
    }

}
