package com.sts.ssms.di

import com.sts.ssms.data.helpdesk.amenity.api.AmenityApi
import com.sts.ssms.data.helpdesk.bylaws.api.ByLawsApi
import com.sts.ssms.data.helpdesk.communication.api.CommunicationApi
import com.sts.ssms.data.helpdesk.conversations.api.ConversationApi
import com.sts.ssms.data.helpdesk.dashboard.api.DashboardApi
import com.sts.ssms.data.helpdesk.documents.api.DocumentsApi
import com.sts.ssms.data.helpdesk.myflat.api.MyFlatApi
import com.sts.ssms.data.helpdesk.notice.api.NoticeBoardApi
import com.sts.ssms.data.helpdesk.notification.api.NotificationApi
import com.sts.ssms.data.helpdesk.payments.api.PaymentApi
import com.sts.ssms.data.helpdesk.photogallery.api.PhotoGalleryApi
import com.sts.ssms.data.helpdesk.profile.api.ProfileApi
import com.sts.ssms.data.helpdesk.societyevent.api.SocietyEventApi
import com.sts.ssms.data.helpdesk.staff.api.StaffApi
import com.sts.ssms.data.helpdesk.vendor.api.VendorAPi
import com.sts.ssms.data.login.api.LoginApi
import com.sts.ssms.data.password.api.PasswordApi
import com.sts.ssms.data.society.api.SocietyApi
import org.koin.dsl.module
import retrofit2.Retrofit

val loginApiModule = module {
    factory { provideLoginApi(retrofit = get()) }
}

val profileApiModule = module {
    factory { provideProfileApi(retrofit = get()) }
}

val societyApiModule = module {
    factory { provideSocietyApi(retrofit = get()) }
}

val passwordApiModule = module {
    factory { providePasswordApi(retrofit = get()) }
}

private fun provideLoginApi(retrofit: Retrofit): LoginApi =
    retrofit.create(LoginApi::class.java)

private fun provideProfileApi(retrofit: Retrofit): ProfileApi =
    retrofit.create(ProfileApi::class.java)

private fun provideSocietyApi(retrofit: Retrofit): SocietyApi =
    retrofit.create(SocietyApi::class.java)

private fun providePasswordApi(retrofit: Retrofit): PasswordApi =
    retrofit.create(PasswordApi::class.java)

private fun provideNotificationApi(retrofit: Retrofit): NotificationApi =
    retrofit.create(NotificationApi::class.java)

private fun provideDashboardApi(retrofit: Retrofit): DashboardApi =
    retrofit.create(DashboardApi::class.java)

private fun provideConversationApi(retrofit: Retrofit): ConversationApi =
    retrofit.create(ConversationApi::class.java)

private fun providePaymentApi(retrofit: Retrofit): PaymentApi =
    retrofit.create(PaymentApi::class.java)

private fun provideMyFlatApi(retrofit: Retrofit): MyFlatApi =
    retrofit.create(MyFlatApi::class.java)

private fun provideVendorApi(retrofit: Retrofit): VendorAPi =
    retrofit.create(VendorAPi::class.java)

private fun provideStaffApi(retrofit: Retrofit): StaffApi =
    retrofit.create(StaffApi::class.java)

private fun provideAmenityApi(retrofit: Retrofit): AmenityApi =
    retrofit.create(AmenityApi::class.java)

private fun provideSocietyEventApi(retrofit: Retrofit): SocietyEventApi =
    retrofit.create(SocietyEventApi::class.java)

private fun provideGalleryApi(retrofit: Retrofit): PhotoGalleryApi =
    retrofit.create(PhotoGalleryApi::class.java)

private fun provideNoticeBoardApi(retrofit: Retrofit): NoticeBoardApi =
    retrofit.create(NoticeBoardApi::class.java)

private fun provideDocumentsApi(retrofit: Retrofit): DocumentsApi =
    retrofit.create(DocumentsApi::class.java)

private fun provideCommunicationApi(retrofit: Retrofit): CommunicationApi =
    retrofit.create(CommunicationApi::class.java)

private fun provideByLawsApi(retrofit: Retrofit): ByLawsApi =
    retrofit.create(ByLawsApi::class.java)

val helpDeskApiModule = module {
    factory { provideConversationApi(retrofit = get()) }
    factory { provideDashboardApi(retrofit = get()) }
    factory { provideNotificationApi(retrofit = get()) }
    factory { providePaymentApi(retrofit = get()) }
    factory { provideMyFlatApi(retrofit = get()) }
    factory { provideVendorApi(retrofit = get()) }
    factory { provideStaffApi(retrofit = get()) }
    factory { provideAmenityApi(retrofit = get()) }
    factory { provideSocietyEventApi(retrofit = get()) }
    factory { provideGalleryApi(retrofit = get()) }
    factory { provideNoticeBoardApi(retrofit = get()) }
    factory { provideCommunicationApi(retrofit = get()) }
    factory { provideByLawsApi(retrofit = get()) }
    factory { provideDocumentsApi(retrofit = get()) }
}
