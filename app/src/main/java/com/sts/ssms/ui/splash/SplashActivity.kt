package com.sts.ssms.ui.splash

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.sts.ssms.data.login.repository.LoginRepository
import com.sts.ssms.data.society.repository.SocietyRepository
import com.sts.ssms.utils.NAVIGATE_TO_HELP_DESK
import com.sts.ssms.utils.NAVIGATE_TO_LOGIN
import com.sts.ssms.utils.navigationTo
import kotlinx.coroutines.*
import org.koin.android.ext.android.inject

class SplashActivity : AppCompatActivity() {

    private val loginRepository by inject<LoginRepository>()
    private val societyRepository: SocietyRepository by inject()

    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        CoroutineScope(Dispatchers.IO).launch {
            delay(500)
            societyRepository.loadSocietyFromRemoteLocal(societyRepository.getSocietyId())
            withContext(Dispatchers.Main) {
                if (loginRepository.isLoggedIn) {
                    val loggedInUser = loginRepository.user
                    if (loggedInUser?.passwordChanged == 1) {
                        navigationTo(NAVIGATE_TO_HELP_DESK)
                        return@withContext
                    }
                }
                navigationTo(NAVIGATE_TO_LOGIN)
            }
        }
    }
}
