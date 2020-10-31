package com.V.VSocial

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class LinksAdapter(var links:List<Contact_and_links>): RecyclerView.Adapter<LinksAdapter.LinkHolder>() {
    inner class LinkHolder(cardView: CardView): RecyclerView.ViewHolder(cardView) {
        var text_v =cardView.findViewById(R.id.name) as TextView
        var button_v =cardView.findViewById(R.id.button) as FloatingActionButton
        fun bind(position: Int){
            text_v.text=links[position].title
            button_v.setImageResource(R.drawable.baseline_link_24)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LinkHolder {
        val cv = LayoutInflater.from(parent.context)
            .inflate(R.layout.link_element, parent, false) as CardView
        return LinkHolder(cv)
    }

    override fun getItemCount(): Int {
        return links.size
    }

    override fun onBindViewHolder(holder: LinkHolder, position: Int) {
        holder.bind(position)
    }
}


