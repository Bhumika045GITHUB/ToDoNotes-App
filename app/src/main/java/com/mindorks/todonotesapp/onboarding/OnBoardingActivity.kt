package com.mindorks.todonotesapp.onboarding

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager.widget.ViewPager
import com.mindorks.todonotesapp.R
import com.mindorks.todonotesapp.utils.PrefConstant
import com.mindorks.todonotesapp.view.LoginActivity

class OnBoardingActivity : AppCompatActivity(), OnBoardingOneFragment.OnNextClick, OnBoardingTwoFragment.OnOptionClick {
    lateinit var viewPager : ViewPager
    lateinit var sharedPreferences: SharedPreferences
    lateinit var editor: SharedPreferences.Editor


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_on_boarding)
        bindView()
        setupSharedPreference()
    }

    private fun setupSharedPreference() {
        sharedPreferences = getSharedPreferences(PrefConstant.SHARED_PREFERENCE_NAME, MODE_PRIVATE)
    }

    fun bindView() {
         viewPager = findViewById(R.id.ViewPager)
         val adapter = FragmentAdapter(supportFragmentManager)
         viewPager.adapter = adapter
    }

    override fun onClick() {
        viewPager.currentItem =  1
    }

    override fun onOptionBack() {
            viewPager.currentItem = 0
    }

    override fun onOptionDone() {
//            2nd fragment
        editor = sharedPreferences.edit()
        editor.putBoolean(PrefConstant.ON_BOARDED_SUCCESSFULLY,true)
        editor.apply()

        val intent = Intent(this@OnBoardingActivity, LoginActivity::class.java)
        startActivity(intent)
    }
}

//1st time
//Splash -> Onboarding -> LOgin -> MyNotes

//2nd time(user not logged in)
//splash -> login -> MyNotes

//splash -> MyNotes

