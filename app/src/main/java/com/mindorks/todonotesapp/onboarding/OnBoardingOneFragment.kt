package com.mindorks.todonotesapp.onboarding

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.mindorks.todonotesapp.R
import kotlinx.android.synthetic.main.fragment_on_boarding_one.*
import kotlinx.android.synthetic.main.fragment_on_boarding_one.textViewNext as textViewNext

class OnBoardingOneFragment : Fragment() {
    lateinit var textViewNext: TextView
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_on_boarding_one, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        bindView(view)
    }

    private fun bindView(view: View) {
        textViewNext = view.findViewById(R.id.textViewNext)
    }

}