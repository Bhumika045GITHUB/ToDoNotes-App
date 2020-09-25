package com.mindorks.todonotesapp.view

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.AutoText
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.mindorks.todonotesapp.R
import com.mindorks.todonotesapp.utils.AppConstant
import com.mindorks.todonotesapp.utils.PrefConstant
import com.mindorks.todonotesapp.utils.StoreSession
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    lateinit var editTextFullName: EditText
    lateinit var editTextUserName: EditText
    lateinit var buttonLogin: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        bindViews()
        setUpSharedPreferences()
    }

    private fun setUpSharedPreferences() {
       StoreSession.init(this)
    }

    private fun bindViews() {
        editTextFullName = findViewById(R.id.editTextFullName)
        editTextUserName = findViewById(R.id.editTextUserName)
        buttonLogin = findViewById(R.id.buttonLogin)
        val clickAction = object : View.OnClickListener{
            override fun onClick(p0: View?) {
                val fullName = editTextFullName.text.toString()
                val UserName = editTextUserName.text.toString()
                if(fullName.isNotEmpty() && UserName.isNotEmpty()) {
                    val intent = Intent(this@LoginActivity,MyNotesActivity::class.java)
                    intent.putExtra(AppConstant.FULL_NAME, fullName)
                    startActivity(intent)
                    saveLoginState()

                } else {
                }
            }
        }
        buttonLogin.setOnClickListener(clickAction)
    }

    private fun saveLoginState() {
        StoreSession.write(PrefConstant.IS_LOGGED_IN, true)
    }

    private fun saveFullName(fullName: String) {
        StoreSession.write(PrefConstant.FULL_NAME, fullName)
    }
}