package com.mindorks.todonotesapp.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mindorks.todonotesapp.R
import com.mindorks.todonotesapp.clicklisteners.ItemClickListener
import com.mindorks.todonotesapp.db.Notes

class NotesAdapter(val list:List<Notes>, val itemClickListener: ItemClickListener) : RecyclerView.Adapter<NotesAdapter.ViewHolder>() {
//    val TAG = "NotesAdapter"
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.notes_adapter_layout, parent, false)
        return ViewHolder(view)
    }
    override fun onBindViewHolder(holder: NotesAdapter.ViewHolder, position: Int) {
        val notes = list[position]
        val title = notes.title
        val description  = notes.description
        holder.textViewTitle.text = title
        holder.textViewDescription.text = description
        Glide.with(holder.itemView).load(notes.imagePath).into(holder.imageView)
        holder.itemView.setOnClickListener(object  : View.OnClickListener{
            override fun onClick(v: View?) {
                itemClickListener.onClick(notes)
            }
        })
        holder.checkBoxMarkStatus.setOnCheckedChangeListener(object  : CompoundButton.OnCheckedChangeListener {
            override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
                notes.isTaskCompleted = isChecked
                itemClickListener.onUpdate(notes)
            }
        })
    }

    override fun getItemCount(): Int {
        return list.size
    }
    inner class ViewHolder(ItemView: View): RecyclerView.ViewHolder(ItemView) {
        val textViewTitle : TextView = itemView.findViewById(R.id.textViewTitle)
        val textViewDescription : TextView = itemView.findViewById(R.id.textViewDescription)
        val checkBoxMarkStatus : CheckBox = itemView.findViewById(R.id.containerMarkStatus)
        val imageView : ImageView = itemView.findViewById(R.id.imageView)
    }

}