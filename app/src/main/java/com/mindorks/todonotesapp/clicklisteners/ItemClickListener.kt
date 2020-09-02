package com.mindorks.todonotesapp.clicklisteners

import com.mindorks.todonotesapp.model.Notes

interface ItemClickListener {
    fun onClick(notes: Notes)
}