package com.sts.ssms.ui.helpdesk.myflat

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sts.ssms.NetworkState
import com.sts.ssms.data.helpdesk.myflat.api.toMyFlatUiModel
import com.sts.ssms.data.helpdesk.myflat.repository.MyFlatRepository
import com.sts.ssms.data.society.model.Flat
import com.sts.ssms.ui.helpdesk.myflat.model.MyFlatUiModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MyFlatViewModel(private val myFlatRepository: MyFlatRepository) : ViewModel() {

    private var _flatOwnerResult = MutableLiveData<List<MyFlatUiModel>>()
    val flatOwnerResult: LiveData<List<MyFlatUiModel>> = _flatOwnerResult

    private var _flatTenantResult = MutableLiveData<List<MyFlatUiModel>>()
    val flatTenantResult: LiveData<List<MyFlatUiModel>> = _flatTenantResult

    private var _networkState = MutableLiveData<NetworkState>()
    val networkState: LiveData<NetworkState> = _networkState

    private var _flatResult = MutableLiveData<List<Flat>>()
    val flatResult: LiveData<List<Flat>> = _flatResult

    private var selectedFlat: Int = -1
    fun getSelectedFlatId() = selectedFlat

    fun myFlatMembers(flatId: Int) {
        _networkState.value = NetworkState.LOADING
        CoroutineScope(Dispatchers.IO).launch {
            val result = myFlatRepository.flatMembersList(flatId)
            withContext(Dispatchers.Main) {
                if (result.myFlatResponse != null) {
                    selectedFlat = flatId
                    _flatOwnerResult.value =
                        result.myFlatResponse.memberList.map { it.toMyFlatUiModel(isOwner = true) }
                    _flatTenantResult.value =
                        result.myFlatResponse.tenantList.map { it.toMyFlatUiModel(isOwner = false) }
                    _networkState.value = NetworkState.LOADED
                } else {
                    _networkState.value = NetworkState.error(result.error)
                }
            }
        }
    }

    fun getUserFlats() {
        _networkState.value = NetworkState.LOADING
        _flatResult.value = myFlatRepository.getFlatsBySociety()
    }

}