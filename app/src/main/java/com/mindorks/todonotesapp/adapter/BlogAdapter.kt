package com.mindorks.todonotesapp.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mindorks.todonotesapp.R
import com.mindorks.todonotesapp.adapter.BlogAdapter.ViewHolder
import com.mindorks.todonotesapp.model.Data


class BlogAdapter(val list:List<Data>): RecyclerView.Adapter<BlogAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.notes_adapter_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val blog = list[position]
        Log.d("BlogAdapter", blog.img_url)
        Glide.with(holder.itemView).load(blog.img_url).into(holder.imageView)
        holder.textViewTitle.text = blog.title
        holder.textViewDescription.text = blog.description
    }

    override fun getItemCount(): Int {
        return list.size
    }
    inner class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val imageView:ImageView = itemView.findViewById(R.id.imageView)
        val textViewTitle : TextView = itemView.findViewById(R.id.textViewTitle)
        val textViewDescription : TextView = itemView.findViewById(R.id.textViewDescription)

    }
}