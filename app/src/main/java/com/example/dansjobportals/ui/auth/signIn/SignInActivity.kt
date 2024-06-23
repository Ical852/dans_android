package com.example.dansjobportals.ui.auth.signIn

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.dansjobportals.R
import com.example.dansjobportals.databinding.ActivitySignInBinding
import com.example.dansjobportals.ui.home.MainActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task

class SignInActivity : AppCompatActivity() {
    private val binding by lazy { ActivitySignInBinding.inflate(layoutInflater) }
    private lateinit var gso: GoogleSignInOptions
    private lateinit var gsc: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        var signInText = binding.googleBtn.getChildAt(0) as TextView;
        signInText.text = "Sign in with Google"

        gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail().build()
        gsc = GoogleSignIn.getClient(this, gso)

        binding.googleBtn.setOnClickListener {
            signIn()
        }
    }

    fun signIn() {
        var signInIntent = gsc.signInIntent
        startActivityForResult(signInIntent, 1000)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode === 1000) {
            var task = GoogleSignIn.getSignedInAccountFromIntent(data)

            try {
                task.getResult(ApiException::class.java)
                navigateToHome()
                Toast.makeText(this, "Something not wrong", Toast.LENGTH_SHORT).show()

            } catch (e: ApiException) {
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun navigateToHome() {
        finish()
        var intent = Intent(this@SignInActivity, MainActivity::class.java)
        startActivity(intent)
    }
}