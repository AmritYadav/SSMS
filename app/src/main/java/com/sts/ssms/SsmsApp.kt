package com.sts.ssms

import android.app.Application
import com.sts.ssms.di.*
import com.sts.ssms.di.ViewModelModule.documentsManagement
import com.sts.ssms.di.ViewModelModule.noticeBoard
import com.sts.ssms.di.ViewModelModule.societyEvent
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.module.Module
class SsmsApp : Application() {

    override fun onCreate() {
        super.onCreate()

        val appModules: MutableList<Module> = ArrayList()
        appModules.add(dbModule)
        appModules.add(networkModule)
        appModules.add(societyApiModule)
        appModules.add(societyRepositoryModule)
        appModules.addAll(loginModules)
        appModules.addAll(helpDeskModules)
        appModules.addAll(passwordModules)

        startKoin {
            androidContext(this@SsmsApp)
            modules(appModules)
        }
    }

    private val loginModules: List<Module> = listOf(
        ViewModelModule.login,
        loginRepositoryModule,
        loginApiModule
    )

    private val vendorManagementModules: List<Module> = listOf(
        ViewModelModule.vendorManagement,
        ViewModelModule.staffManagement
    )
    private val photoGalleryManagementModules: List<Module> = listOf(
        ViewModelModule.photoGalleryManagement
    )
    private val profileModules: List<Module> = listOf(
        profileRepositoryModule,
        profileApiModule,
        ViewModelModule.profile
    )
    private val noticeBoardModule: List<Module> = listOf(
        noticeBoard
    )
    private val documentsModule: List<Module> = listOf(
        documentsManagement
    )

    private val helpDeskModules: List<Module> = listOf(ViewModelModule.helpDesk)
        .asSequence()
        .plus(helpDeskApiModule)
        .plus(vendorManagementModules)
        .plus(photoGalleryManagementModules)
        .plus(profileModules)
        .plus(helpDeskRepositoryModule)
        .plus(noticeBoardModule)
        .plus(societyEvent)
        .plus(documentsModule)
        .toList()

    private val passwordModules: List<Module> = listOf(ViewModelModule.passwordViewModel)
        .plus(passwordApiModule)
        .plus(passwordRepositoryModule)
}

