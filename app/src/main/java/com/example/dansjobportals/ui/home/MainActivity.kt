package com.example.dansjobportals.ui.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.dansjobportals.R
import com.example.dansjobportals.databinding.ActivityMainBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions

class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private lateinit var gso: GoogleSignInOptions
    private lateinit var gsc: GoogleSignInClient

        override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val navController = findNavController(R.id.nav_host_fragment)
        binding.navView.setupWithNavController(navController)

        gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail().build()
        gsc = GoogleSignIn.getClient(this, gso)

        var account = GoogleSignIn.getLastSignedInAccount(this)
        if (account != null) {
            val bundle = Bundle()
            bundle.putString("fullName", account.displayName)
        }
    }
}