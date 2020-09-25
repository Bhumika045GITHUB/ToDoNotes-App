package com.mindorks.todonotesapp.onboarding

import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.fragment.app.Fragment as Fragment

class FragmentAdapter(fragmentManager: FragmentManager) : FragmentStatePagerAdapter(fragmentManager) {
    override fun getCount(): Int {
        return 2
    }

    override fun getItem(position: Int): Fragment {
        if (position == 0) {
            return OnBoardingOneFragment()
        } else {
            return OnBoardingTwoFragment()
        }
    }
}