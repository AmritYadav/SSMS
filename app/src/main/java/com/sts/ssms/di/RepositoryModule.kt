package com.sts.ssms.di

import com.sts.ssms.data.database.AppDatabase
import com.sts.ssms.data.database.dao.EventDao
import com.sts.ssms.data.event.EventRepository
import com.sts.ssms.data.helpdesk.amenity.repository.AmenityRepository
import com.sts.ssms.data.helpdesk.amenity.repository.LocalAmenityDataSource
import com.sts.ssms.data.helpdesk.amenity.repository.RemoteAmenityDataSource
import com.sts.ssms.data.helpdesk.bylaws.repository.ByLawsRemoteDataSource
import com.sts.ssms.data.helpdesk.bylaws.repository.ByLawsRepository
import com.sts.ssms.data.helpdesk.communication.repository.CommRemoteDataSource
import com.sts.ssms.data.helpdesk.communication.repository.CommRepository
import com.sts.ssms.data.helpdesk.conversations.repository.PostRemoteDataSource
import com.sts.ssms.data.helpdesk.conversations.repository.PostRepository
import com.sts.ssms.data.helpdesk.dashboard.repository.DashboardRemoteDataSource
import com.sts.ssms.data.helpdesk.dashboard.repository.DashboardRepository
import com.sts.ssms.data.helpdesk.documents.repository.DocumentsRemoteDataSource
import com.sts.ssms.data.helpdesk.documents.repository.DocumentsRepository
import com.sts.ssms.data.helpdesk.features.repository.FeaturesLocalDataSource
import com.sts.ssms.data.helpdesk.features.repository.FeaturesRepository
import com.sts.ssms.data.helpdesk.myflat.repository.MyFlatLocalDataSource
import com.sts.ssms.data.helpdesk.myflat.repository.MyFlatRemoteDataSource
import com.sts.ssms.data.helpdesk.myflat.repository.MyFlatRepository
import com.sts.ssms.data.helpdesk.notice.repository.NoticeRemoteDataSource
import com.sts.ssms.data.helpdesk.notice.repository.NoticeRepository
import com.sts.ssms.data.helpdesk.notification.repository.NotificationRemoteDataSource
import com.sts.ssms.data.helpdesk.notification.repository.NotificationRepository
import com.sts.ssms.data.helpdesk.payments.repository.PaymentRemoteDataSource
import com.sts.ssms.data.helpdesk.payments.repository.PaymentRepository
import com.sts.ssms.data.helpdesk.photogallery.repository.GalleryRemoteDataSource
import com.sts.ssms.data.helpdesk.photogallery.repository.GalleryRepository
import com.sts.ssms.data.helpdesk.profile.repository.ProfileRemoteDataSource
import com.sts.ssms.data.helpdesk.profile.repository.ProfileRepository
import com.sts.ssms.data.helpdesk.societyevent.repository.RemoteSocietyEventDataSource
import com.sts.ssms.data.helpdesk.societyevent.repository.SocietyEventRepository
import com.sts.ssms.data.helpdesk.staff.repository.StaffRemoteDataSource
import com.sts.ssms.data.helpdesk.staff.repository.StaffRepository
import com.sts.ssms.data.helpdesk.vendor.repository.VendorRemoteDataSource
import com.sts.ssms.data.helpdesk.vendor.repository.VendorRepository
import com.sts.ssms.data.login.repository.AuthTokenLocalDataSource
import com.sts.ssms.data.login.repository.LoginRepository
import com.sts.ssms.data.login.repository.local.LoginLocalDataSource
import com.sts.ssms.data.login.repository.local.LoginLocalDataSourceImpl
import com.sts.ssms.data.login.repository.remote.LoginRemoteDataSource
import com.sts.ssms.data.login.repository.remote.LoginRemoteDataSourceImpl
import com.sts.ssms.data.password.change.ChangePwdRemoteDataSource
import com.sts.ssms.data.password.change.ChangePwdRepository
import com.sts.ssms.data.password.forgot.ForgotPwdRemoteDataSource
import com.sts.ssms.data.password.forgot.ForgotPwdRepository
import com.sts.ssms.data.password.reset.ResetPwdRemoteDataSource
import com.sts.ssms.data.password.reset.ResetPwdRepository
import com.sts.ssms.data.society.repository.SocietyLocalDataSource
import com.sts.ssms.data.society.repository.SocietyRemoteDataSource
import com.sts.ssms.data.society.repository.SocietyRepository
import org.koin.core.module.Module
import org.koin.dsl.module

val loginRepositoryModule = module {
    single { AuthTokenLocalDataSource(get()) }
    factory<LoginLocalDataSource> {
        LoginLocalDataSourceImpl(societyRepository = get(), prefs = get(), appDatabase = get())
    }
    factory<LoginRemoteDataSource> {
        LoginRemoteDataSourceImpl(
            authTokenLocalDataSource = get(),
            loginApi = get(),
            societyApi = get()
        )
    }
    factory { LoginRepository(loginLocalDataSource = get(), loginRemoteDataSource = get()) }
}

val profileRepositoryModule = module {
    factory { ProfileRemoteDataSource(profileApi = get()) }
    factory { ProfileRepository(loginRepository = get(), profileRemoteDataSource = get()) }
}

val societyRepositoryModule = module {
    factory { SocietyLocalDataSource(prefs = get(), appDatabase = get()) }
    factory { SocietyRemoteDataSource(societyApi = get()) }
    single {
        SocietyRepository(societyLocalDataSource = get(), societyRemoteDataSource = get())
    }
}

val passwordRepositoryModule = module {
    factory { ResetPwdRemoteDataSource(passwordApi = get()) }
    factory { ResetPwdRepository(resetPwdRemoteDataSource = get()) }
    factory { ChangePwdRemoteDataSource(passwordApi = get()) }
    factory { ChangePwdRepository(changePwdRemoteDataSource = get()) }
    factory { ForgotPwdRemoteDataSource(passwordApi = get()) }
    factory { ForgotPwdRepository(remoteDataSource = get()) }
}

val conversationRepositoryModule = module {
    factory { PostRemoteDataSource(conversationsApi = get()) }
    factory { PostRepository(remoteDataSource = get()) }
}

val paymentRepositoryModule = module {
    factory { PaymentRemoteDataSource(paymentApi = get()) }
    factory { PaymentRepository(remoteDataSource = get()) }
}
val myFlatRepositoryModule = module {
    factory { MyFlatRemoteDataSource(myFlatApi = get()) }
    factory { MyFlatLocalDataSource(societyRepository = get()) }
    factory { MyFlatRepository(localDataSource = get(), remoteDataSource = get()) }
}
val vendorRepositoryModule = module {
    factory { VendorRemoteDataSource(vendorAPi = get()) }
    factory { VendorRepository(remoteDataSource = get()) }
}

val staffRepositoryModule = module {
    factory { StaffRemoteDataSource(staffApi = get()) }
    factory { StaffRepository(remoteDataSource = get()) }
}

val dashboardRepositoryModule = module {
    factory { DashboardRemoteDataSource(dashboardApi = get()) }
    factory { DashboardRepository(remoteDataSource = get()) }
}

val notificationRepositoryModule = module {
    factory { NotificationRemoteDataSource(notificationApi = get()) }
    factory { NotificationRepository(remoteDataSource = get()) }
}

val amenityRepositoryModule = module {
    factory { LocalAmenityDataSource(eventDao = provideEventDao(appDatabase = get())) }
    factory { RemoteAmenityDataSource(amenityApi = get()) }
    factory { AmenityRepository(localAmenityDataSource = get(), remoteAmenityDataSource = get()) }
}

val societyEventRepositoryModule = module {
    factory { RemoteSocietyEventDataSource(societyEventApi = get()) }
    factory { SocietyEventRepository( remoteDataSource = get()) }
}

val eventRepositoryModule = module {
    factory { EventRepository(amenityRepository = get()) }
}

private fun provideEventDao(appDatabase: AppDatabase): EventDao = appDatabase.getEventDao()

val photoGalleryRepoModule = module {
    factory { GalleryRemoteDataSource(photoGalleryApi = get()) }
    factory { GalleryRepository(remoteDataSource = get()) }
}

val featuresRepositoryModule = module {
    factory { FeaturesLocalDataSource() }
    factory { FeaturesRepository(localDataSource = get()) }
}

val byLawsRepositoryModule = module {
    factory { ByLawsRemoteDataSource(byLawsApi = get()) }
    factory { ByLawsRepository(byLawsRemoteDataSource = get()) }
}

val noticeRepositoryModule = module {
    factory { NoticeRemoteDataSource(noticeBoardApi = get()) }
    factory { NoticeRepository(remoteDataSource = get()) }
}
val documentRepositoryModule = module {
    factory { DocumentsRemoteDataSource(documentsApi = get()) }
    factory { DocumentsRepository(remoteDataSource = get()) }
}

val communicationRepoModule = module {
    factory { CommRemoteDataSource(communicationApi = get()) }
    factory { CommRepository(remoteDataSource = get()) }
}

val helpDeskRepositoryModule: List<Module> = listOf(
    dashboardRepositoryModule,
    conversationRepositoryModule,
    paymentRepositoryModule,
    myFlatRepositoryModule,
    vendorRepositoryModule,
    staffRepositoryModule,
    notificationRepositoryModule,
    amenityRepositoryModule,
    societyEventRepositoryModule,
    eventRepositoryModule,
    photoGalleryRepoModule,
    featuresRepositoryModule,
    byLawsRepositoryModule,
    noticeRepositoryModule,
    communicationRepoModule,
    documentRepositoryModule
)
