package com.v.vsocial.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.v.vsocial.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.v.vsocial.databinding.LinkElementBinding
import com.v.vsocial.models.ContactsLinks

class LinksAdapter(var links:List<ContactsLinks>?): RecyclerView.Adapter<LinksAdapter.LinkHolder>() {

    inner class LinkHolder(val binding: LinkElementBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(position: Int){
            binding.name.text= links?.get(position)?.title
            binding.button.setImageResource(R.drawable.baseline_link_24)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LinkHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = LinkElementBinding.inflate(inflater)
        return LinkHolder(binding)
    }

    override fun getItemCount(): Int {
        if(links == null){
            return 0
        }else{
            return links!!.size
        }

    }

    override fun onBindViewHolder(holder: LinkHolder, position: Int) {
        holder.bind(position)
    }
}


