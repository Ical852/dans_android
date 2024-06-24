package com.example.dansjobportals.ui.auth.signIn

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import com.example.dansjobportals.databinding.ActivitySignInBinding
import com.example.dansjobportals.ui.home.MainActivity
import com.example.dansjobportals.viewModels.auth.SignInViewModel
import org.koin.dsl.module
import org.koin.androidx.viewmodel.ext.android.viewModel

val signInModule = module {
    factory { SignInActivity() }
}

class SignInActivity : AppCompatActivity() {
    private val binding by lazy { ActivitySignInBinding.inflate(layoutInflater) }
    private val viewModel: SignInViewModel by viewModel()
    private val reqCode: Int = 123

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        var signInText = binding.googleBtn.getChildAt(0) as TextView;
        signInText.text = "Sign in with Google"

        binding.googleBtn.setOnClickListener {
            val signIntent = viewModel.signInWithGoogle()
            startActivityForResult(signIntent, reqCode)
        }

        viewModel.signInResult.observe(this, Observer { isSignedIn ->
            if (isSignedIn) {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
        })

        viewModel.errorMessage.observe(this, Observer { error ->
            error?.let {
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == reqCode) {
            viewModel.handleSignInResult(data)
        }
    }
}