package com.v.vsocial

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.v.vsocial.adapters.LinksAdapter
import com.v.vsocial.databinding.FragmentMainUserBinding
import com.v.vsocial.models.ContactsLinks
import com.v.vsocial.models.User
import com.v.vsocial.network.Auth
import com.v.vsocial.utils.Action
import com.v.vsocial.utils.State
import com.v.vsocial.viewmodels.MainActivityViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


class UserFragment : Fragment() {
    val model: MainActivityViewModel by lazy { ViewModelProvider(requireActivity()).get(MainActivityViewModel::class.java) }
    lateinit var binding:FragmentMainUserBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding =FragmentMainUserBinding.inflate(inflater,container,false)
        binding.rv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        lifecycleScope.launch {
            model.stateFlow.collect {
                when(it){
                    is State.networkError -> VMNetworkError()
                    is State.loading -> VMLoading()
                    is State.success -> VMSuccsess(it.data,it.label)


                }

            }
        }
        lifecycleScope.launch {
            model.actionFlow.collect {
                when(it){
                    Action.logout -> {
                        Auth.removeUserCredentials(requireContext())
                        startActivity(Intent(requireContext(), LoginActivity::class.java))
                    }
                }
            }
        }


        return binding.root
    }

    private fun VMLoading() {
        binding.progressbar.visibility = View.VISIBLE
    }

    fun VMNetworkError(){
        Toast.makeText(requireContext(), "No internet connection", Toast.LENGTH_SHORT).show()
    }

    fun VMSuccsess(data:Any,label:String){
        binding.progressbar.visibility = View.GONE
        when(label){
            "user" -> binding.user= data as User
            "contacts" -> binding.rv.adapter=LinksAdapter(data as List<ContactsLinks>)
        }
    }

}
