package com.example.dansjobportals.ui.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.dansjobportals.R
import com.example.dansjobportals.ui.auth.signIn.SignInActivity
import com.example.dansjobportals.ui.home.MainActivity
import com.google.firebase.auth.FirebaseAuth

class SplashActivity : AppCompatActivity() {
    private val firebaseAuth by lazy { FirebaseAuth.getInstance() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler().postDelayed({
            if (firebaseAuth.currentUser != null) {
                var mainIntent = Intent(this@SplashActivity, MainActivity::class.java)
                startActivity(mainIntent)
            } else {
                var singInIntent = Intent(this@SplashActivity, SignInActivity::class.java)
                startActivity(singInIntent)
                finish()
            }
        }, 2000)
    }
}