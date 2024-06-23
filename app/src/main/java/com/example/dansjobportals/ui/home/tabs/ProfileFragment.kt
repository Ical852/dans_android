package com.example.dansjobportals.ui.home.tabs

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.dansjobportals.R
import com.example.dansjobportals.databinding.FragmentHomeBinding
import com.example.dansjobportals.databinding.FragmentProfileBinding
import com.example.dansjobportals.ui.auth.signIn.SignInActivity
import com.example.dansjobportals.ui.home.MainActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import org.koin.dsl.module

val profileModule = module {
    factory { ProfileFragment() }
}

class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    private lateinit var gso: GoogleSignInOptions
    private lateinit var gsc: GoogleSignInClient

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(layoutInflater, container,  false)
        gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail().build()
        gsc = GoogleSignIn.getClient(requireContext(), gso)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val receive = arguments?.getString("fullName")
        receive?.let {
            binding.username.text = it
        }

        binding.signOutBtn.setOnClickListener {
            signOut()
        }
    }

    fun signOut() {
        gsc.signOut().addOnCompleteListener{
            val activity = requireActivity() as MainActivity
            activity.finish()
            startActivity(Intent(requireContext(), SignInActivity::class.java))
        }
    }
}