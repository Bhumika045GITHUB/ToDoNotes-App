package com.mindorks.todonotesapp.view

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.iid.FirebaseInstanceId
import com.mindorks.todonotesapp.R
import com.mindorks.todonotesapp.onboarding.OnBoardingActivity
import com.mindorks.todonotesapp.utils.PrefConstant
import com.mindorks.todonotesapp.utils.StoreSession

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        setupSharedPreference()
        checkLoginStatus()
//        getFCMToken()
    }

    private fun getFCMToken() {
        FirebaseInstanceId.getInstance().instanceId
                .addOnCompleteListener(OnCompleteListener { task ->
                    if (!task.isSuccessful) {
                        Log.w("SplashActivity", "getInstanceId failed", task.exception)
                        return@OnCompleteListener
                    }

                    // Get new Instance ID token
                    val token = task.result?.token

                    // Log and toast
                    if (token != null) {
                        Log.d("SplashActivity", token)
                    }

                    Toast.makeText(baseContext, token, Toast.LENGTH_SHORT).show()
                })
    }

    private fun setupSharedPreference() {
      StoreSession.init(this)
    }
    private fun checkLoginStatus() {
        val isLoggedIn = StoreSession.read(PrefConstant.IS_LOGGED_IN)
        val isBoardingSuccess = StoreSession.read(PrefConstant.ON_BOARDED_SUCCESSFULLY)
        if(isLoggedIn!!) {
            val intent = Intent(this@SplashActivity,MyNotesActivity::class.java)
            startActivity(intent)
        } else {
//            if one boarded succes  -> login
//            else onBoardingActivity
            if(isBoardingSuccess!!) {
                val intent = Intent(this@SplashActivity,LoginActivity::class.java)
                startActivity(intent)
            } else {
                val intent = Intent(this@SplashActivity,OnBoardingActivity::class.java)
                startActivity(intent)
            }

        }
    }


}
