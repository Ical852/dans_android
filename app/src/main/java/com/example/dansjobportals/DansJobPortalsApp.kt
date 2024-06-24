package com.example.dansjobportals

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.example.dansjobportals.ui.auth.signIn.signInModule
import com.example.dansjobportals.ui.home.tabs.homeModule
import com.example.dansjobportals.ui.home.tabs.profileModule
import com.example.dansjobportals.viewModels.auth.signInViewModel
import com.example.dansjobportals.viewModels.home.profileViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import timber.log.Timber

class DansJobPortalsApp: Application() {
    override fun onCreate() {
        super.onCreate()
        Timber.plant( Timber.DebugTree() )
        Timber.e("Run Dans Job Portals Base App")
        AppCompatDelegate.setDefaultNightMode( AppCompatDelegate.MODE_NIGHT_NO )
        startKoin {
            androidLogger(Level.ERROR)
            androidContext( this@DansJobPortalsApp )
            modules(
                listOf(
                    homeModule,
                    profileModule,
                    signInViewModel,
                    signInModule,
                    profileViewModel,
                    profileModule,
                )
            )
        }
    }
}