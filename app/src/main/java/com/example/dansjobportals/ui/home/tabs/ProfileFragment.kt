package com.example.dansjobportals.ui.home.tabs

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.example.dansjobportals.R
import com.example.dansjobportals.databinding.FragmentProfileBinding
import com.example.dansjobportals.ui.auth.signIn.SignInActivity
import com.example.dansjobportals.viewModels.home.ProfileViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import org.koin.dsl.module
import org.koin.androidx.viewmodel.ext.android.viewModel

val profileModule = module {
    factory { ProfileFragment() }
}

class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    private val viewModel: ProfileViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(layoutInflater, container,  false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.signOutBtn.setOnClickListener {
            viewModel.signOut()
        }

        viewModel.signOutEvent.observe(viewLifecycleOwner, Observer { isSignedOut ->
            if (isSignedOut) {
                val intent = Intent(activity, SignInActivity::class.java)
                startActivity(intent)
                activity?.finish()
            }
        })

        viewModel.user.observe(viewLifecycleOwner, Observer { user ->
            if (user != null) {
                binding.username.text = user.displayName
            }
        })
    }

}