package com.sts.ssms.di

import com.sts.ssms.paging.amenity.allrequest.AllRequestAmenityDataSourceFactory
import com.sts.ssms.paging.amenity.societyamenity.SocietyAmenityDataSourceFactory
import com.sts.ssms.paging.conversation.PostDataSourceFactory
import com.sts.ssms.paging.documents.DocumentsDataSourceFactory
import com.sts.ssms.paging.noticeboard.NoticeDataSourceFactory
import com.sts.ssms.paging.payments.PaymentDataSourceFactory
import com.sts.ssms.paging.photogallery.PhotoGalleryDataSourceFactory
import com.sts.ssms.paging.societyevent.SocietyEventDataSourceFactory
import com.sts.ssms.paging.staff.StaffDataSourceFactory
import com.sts.ssms.paging.vendor.VendorDataSourceFactory
import com.sts.ssms.ui.helpdesk.HelpDeskViewModel
import com.sts.ssms.ui.helpdesk.amenity.viewmodels.AmenityAllRequestViewModel
import com.sts.ssms.ui.helpdesk.amenity.viewmodels.AmenityMyRequestViewModel
import com.sts.ssms.ui.helpdesk.amenity.viewmodels.NewRequestAmenityViewModel
import com.sts.ssms.ui.helpdesk.amenity.viewmodels.SocietyAmenityViewModel
import com.sts.ssms.ui.helpdesk.bylaws.ByLawsViewModel
import com.sts.ssms.ui.helpdesk.conversations.viewmodel.NewPostViewModel
import com.sts.ssms.ui.helpdesk.conversations.viewmodel.PostViewModel
import com.sts.ssms.ui.helpdesk.dashboard.viewmodel.DashboardDetailsViewModel
import com.sts.ssms.ui.helpdesk.dashboard.viewmodel.DashboardViewModel
import com.sts.ssms.ui.helpdesk.documents.viewmodel.DocumentViewModel
import com.sts.ssms.ui.helpdesk.features.FeaturesViewModel
import com.sts.ssms.ui.helpdesk.myflat.MyFlatViewModel
import com.sts.ssms.ui.helpdesk.noticeboard.viewmodels.NoticeViewModel
import com.sts.ssms.ui.helpdesk.officialcommunication.viewmodels.CommDetailsViewModel
import com.sts.ssms.ui.helpdesk.officialcommunication.viewmodels.CommunicationViewModel
import com.sts.ssms.ui.helpdesk.paymentdetails.PaymentViewModel
import com.sts.ssms.ui.helpdesk.photogallery.viewmodel.AlbumViewModel
import com.sts.ssms.ui.helpdesk.photogallery.viewmodel.PhotoGalleryViewModel
import com.sts.ssms.ui.helpdesk.profile.viewmodel.ProfileViewModel
import com.sts.ssms.ui.helpdesk.profile.viewmodel.SocietyViewModel
import com.sts.ssms.ui.helpdesk.societyevent.viewmodel.SocietyEventViewModel
import com.sts.ssms.ui.helpdesk.societyevent.viewmodel.SuggestActivityViewModel
import com.sts.ssms.ui.helpdesk.staff.viewmodel.StaffDetailsViewModel
import com.sts.ssms.ui.helpdesk.staff.viewmodel.StaffRatingViewModel
import com.sts.ssms.ui.helpdesk.staff.viewmodel.StaffViewModel
import com.sts.ssms.ui.helpdesk.vendor.viewmodel.VendorDetailsViewModel
import com.sts.ssms.ui.helpdesk.vendor.viewmodel.VendorRatingViewModel
import com.sts.ssms.ui.helpdesk.vendor.viewmodel.VendorViewModel
import com.sts.ssms.ui.login.viewmodel.LoginViewModel
import com.sts.ssms.ui.password.change.ChangePwdViewModel
import com.sts.ssms.ui.password.forgot.ForgotPasswordViewModel
import com.sts.ssms.ui.password.reset.ResetPasswordViewModel
import com.sts.ssms.ui.search.amenity.SearchAllAmenityReqViewModel
import com.sts.ssms.ui.search.documents.SearchDocumentsViewModel
import com.sts.ssms.ui.search.noticeboard.SearchNoticeViewModel
import com.sts.ssms.ui.search.photogallery.SearchAlbumViewModel
import com.sts.ssms.ui.search.staff.SearchStaffViewModel
import com.sts.ssms.ui.search.vendor.SearchVendorViewModel
import com.sts.ssms.ui.verifyotp.VerifyOtpViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object ViewModelModule {
    val login = module { viewModel { LoginViewModel(get()) } }

    val passwordViewModel = module {
        viewModel { ResetPasswordViewModel(resetPwdRepository = get()) }
        viewModel { ChangePwdViewModel(changePwdRepository = get()) }
        viewModel { ForgotPasswordViewModel(forgotPwdRepository = get()) }
        viewModel { VerifyOtpViewModel(forgotPwdRepository = get()) }
    }

    val vendorManagement = module {
        factory { VendorDataSourceFactory(vendorRepository = get()) }
        viewModel { VendorViewModel(vendorDataSourceFactory = get()) }
        viewModel { VendorDetailsViewModel(vendorRepository = get()) }
        viewModel { VendorRatingViewModel(vendorRepository = get()) }
    }

    val photoGalleryManagement = module {
        factory { PhotoGalleryDataSourceFactory(photoGalleryRepository = get()) }
        viewModel { PhotoGalleryViewModel(photoGalleryDataSourceFactory = get()) }
    }

    val staffManagement = module {
        factory { StaffDataSourceFactory(staffRepository = get()) }
        viewModel { StaffViewModel(staffDataSourceFactory = get()) }
        viewModel { StaffDetailsViewModel(staffRepository = get()) }
        viewModel { StaffRatingViewModel(staffRepository = get()) }
    }

    val noticeBoard = module {
        factory { NoticeDataSourceFactory(noticeRepository = get()) }
        viewModel { NoticeViewModel(noticeDataSourceFactory = get()) }
    }

    val societyEvent = module {
        factory { SocietyEventDataSourceFactory(societyEventRepository = get()) }
        viewModel { SocietyEventViewModel(societyEventDataSourceFactory = get()) }
        viewModel { SuggestActivityViewModel(societyEventRepository = get()) }
    }

    val documentsManagement = module {
        factory { DocumentsDataSourceFactory(documentsRepository = get()) }
        viewModel { DocumentViewModel(documentsDataSourceFactory = get()) }
    }

    val helpDesk = module {
        viewModel {
            HelpDeskViewModel(
                societyRepository = get(),
                loginRepository = get(),
                notificationRepository = get()
            )
        }
        viewModel { DashboardViewModel(dashboardRepository = get()) }
        viewModel { DashboardDetailsViewModel(dashboardRepository = get()) }

        factory { PostDataSourceFactory(postRepository = get()) }
        viewModel { PostViewModel(postRepository = get(), postDataSourceFactory = get()) }
        viewModel { NewPostViewModel(postRepository = get()) }

        factory { PaymentDataSourceFactory(paymentRepository = get()) }
        viewModel { PaymentViewModel(dataSourceFactory = get()) }

        viewModel { MyFlatViewModel(myFlatRepository = get()) }

        viewModel { SearchVendorViewModel(vendorRepository = get()) }
        viewModel { SearchStaffViewModel(staffRepository = get()) }
        viewModel { SearchNoticeViewModel(noticeRepository = get()) }
        viewModel { SearchDocumentsViewModel(documentsRepository = get()) }

        factory { SocietyAmenityDataSourceFactory(amenityRepository = get()) }
        factory { AllRequestAmenityDataSourceFactory(amenityRepository = get()) }
        viewModel { SocietyAmenityViewModel(dataSourceFactory = get()) }
        viewModel { AmenityMyRequestViewModel(amenityRepository = get()) }
        viewModel { AmenityAllRequestViewModel(dataSourceFactory = get()) }
        viewModel { SearchAllAmenityReqViewModel(amenityRepository = get()) }
        viewModel { NewRequestAmenityViewModel(amenityRepository = get()) }

        viewModel { SocietyViewModel(societyRepository = get()) }
        viewModel { SearchAlbumViewModel(galleryRepository = get()) }
        viewModel { AlbumViewModel(galleryRepository = get()) }
        viewModel { FeaturesViewModel(featuresRepository = get()) }
        viewModel { ByLawsViewModel(byLawsRepository = get()) }
        viewModel { CommunicationViewModel(repository = get()) }
        viewModel { CommDetailsViewModel(commRepository = get()) }
    }

    val profile = module {
        single { ProfileViewModel(profileRepository = get()) }
    }
}