package com.example.dansjobportals.ui.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.dansjobportals.R
import com.example.dansjobportals.ui.auth.signIn.SignInActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler().postDelayed({
            var mainIntent = Intent(this@SplashActivity, SignInActivity::class.java)
            startActivity(mainIntent)
            finish()
        }, 2000)
    }
}