package com.example.dansjobportals.viewModels.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.dansjobportals.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val profileViewModel = module {
    viewModel { ProfileViewModel(get()) }
}

class ProfileViewModel(application: Application): AndroidViewModel(application) {
    private val firebaseAuth by lazy { FirebaseAuth.getInstance() }
    private lateinit var googleSignInClient: GoogleSignInClient
    private val context = getApplication<Application>().applicationContext

    val signOutEvent = MutableLiveData<Boolean>()
    val user = MutableLiveData<FirebaseUser>()

    init {
        initGoogleSignInClient()
        user.postValue(firebaseAuth.currentUser)
    }

    private fun initGoogleSignInClient() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(context.getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(context, gso)
    }

    fun signOut() {
        firebaseAuth.signOut()
        googleSignInClient.signOut().addOnCompleteListener {
            signOutEvent.postValue(true)
        }
    }
}