package com.col740.myhospital

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.signup.*

class Signup: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.signup)
        loginbutton.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            ContextCompat.startActivity(this, intent, null)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        Toast.makeText(this, "Press Back Button Again to Exit", Toast.LENGTH_LONG).show()
    }
}