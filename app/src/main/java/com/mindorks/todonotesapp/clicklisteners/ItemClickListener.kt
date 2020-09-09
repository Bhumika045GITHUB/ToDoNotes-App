package com.mindorks.todonotesapp.clicklisteners

import com.mindorks.todonotesapp.db.Notes


interface ItemClickListener {
    fun onClick(notes: Notes)
    fun onUpdate(notes: Notes)

}