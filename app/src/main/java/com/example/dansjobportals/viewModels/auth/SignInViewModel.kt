package com.example.dansjobportals.viewModels.auth

import android.app.Application
import android.content.Intent
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.dansjobportals.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val signInViewModel = module {
    viewModel { SignInViewModel(get())}
}

class SignInViewModel(application: Application): AndroidViewModel(application) {
    private val firebaseAuth by lazy { FirebaseAuth.getInstance() }
    private lateinit var googleSignInClient: GoogleSignInClient
    private val context = getApplication<Application>().applicationContext

    val signInResult = MutableLiveData<Boolean>()
    val errorMessage = MutableLiveData<String?>()
    val user  = MutableLiveData<GoogleSignInAccount>()

    init {
        initGoogleSignInClient()
    }

    private fun initGoogleSignInClient() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(context.getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(context, gso)
    }

    private fun firebaseAuthWithGoogle(account: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                signInResult.postValue(true)
            } else {
                signInResult.postValue(false)
            }
        }
    }

    fun signInWithGoogle(): Intent {
        return googleSignInClient.signInIntent
    }

    fun handleSignInResult(data: Intent?) {
        val task = GoogleSignIn.getSignedInAccountFromIntent(data)
        try {
            val account = task.getResult(ApiException::class.java)
            if (account != null) {
                firebaseAuthWithGoogle(account)
                user.postValue(account)
            } else {
                signInResult.postValue(false)
            }
        } catch (e: ApiException) {
            errorMessage.postValue(e.toString())
            signInResult.postValue(false)
        }
    }
}