package com.sts.ssms.ui.helpdesk

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sts.ssms.data.helpdesk.notification.repository.NotificationRepository
import com.sts.ssms.data.login.model.LoggedInUser
import com.sts.ssms.data.login.repository.LoginRepository
import com.sts.ssms.data.society.model.Flat
import com.sts.ssms.data.society.repository.SocietyRepository
import com.sts.ssms.ui.helpdesk.notification.model.SsmsNotification
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HelpDeskViewModel(
    private val societyRepository: SocietyRepository,
    private val loginRepository: LoginRepository,
    private val notificationRepository: NotificationRepository
) : ViewModel() {

    private var _isLoggedOut = MutableLiveData<Boolean>()
    private var _loggedInUser = MutableLiveData<LoggedInUser>()

    init {
        _loggedInUser.value = loginRepository.user
    }

    val loggedInUser: LiveData<LoggedInUser>? = _loggedInUser

    val isLoggedOut: LiveData<Boolean>
        get() = _isLoggedOut

    val menuList: List<Int>
        get() = societyRepository.menuList

    val flatList: List<Flat>?
        get() = societyRepository.flatList

    private var _notificationResult = MutableLiveData<SsmsNotification>()
    val notificationResult: LiveData<SsmsNotification> get() = _notificationResult

    fun checkNotifications() {
        CoroutineScope(Dispatchers.IO).launch {
            val result = notificationRepository.getNotifications()
            if (result.notification != null) {
                withContext(Dispatchers.Main) { _notificationResult.value = result.notification }
            }
        }
    }

    fun logout() {
        _isLoggedOut.value = loginRepository.logout()
    }
}