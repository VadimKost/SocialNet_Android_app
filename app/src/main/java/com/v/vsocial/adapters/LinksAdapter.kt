package com.v.vsocial.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.v.vsocial.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.v.vsocial.models.ContactsLinks

class LinksAdapter(var links:List<ContactsLinks>?): RecyclerView.Adapter<LinksAdapter.LinkHolder>() {
    inner class LinkHolder(cardView: CardView): RecyclerView.ViewHolder(cardView) {
        var text_v =cardView.findViewById(R.id.name) as TextView
        var button_v =cardView.findViewById(R.id.button) as FloatingActionButton
        fun bind(position: Int){
            text_v.text= links?.get(position)?.title
            button_v.setImageResource(R.drawable.baseline_link_24)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LinkHolder {
        val cv = LayoutInflater.from(parent.context)
            .inflate(R.layout.link_element, parent, false) as CardView
        return LinkHolder(cv)
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


