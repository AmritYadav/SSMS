package com.sts.ssms.ui.helpdesk.amenity.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.sts.ssms.NetworkState
import com.sts.ssms.data.helpdesk.amenity.model.EventResult
import com.sts.ssms.paging.amenity.societyamenity.SocietyAmenityDataSourceFactory
import com.sts.ssms.ui.helpdesk.amenity.model.SocietyAmenity
import com.sts.ssms.ui.helpdesk.amenity.model.SocietyBuilding
import kotlinx.coroutines.*

private const val PAGE_SIZE = 10

class SocietyAmenityViewModel(private val dataSourceFactory: SocietyAmenityDataSourceFactory) :
    ViewModel() {

    private val job = Job()
    private val coroutineScope = CoroutineScope(Dispatchers.IO + job)

    private var _eventResult = MutableLiveData<EventResult>()
    val eventResult: LiveData<EventResult> = _eventResult

    var allAmenities: LiveData<PagedList<SocietyAmenity>>

    var networkState: LiveData<NetworkState>? = null
    var refreshState: LiveData<NetworkState>? = null

    private var _societyBuildingResult = MutableLiveData<List<SocietyBuilding>>()
    val societyBuildingResult: LiveData<List<SocietyBuilding>> = _societyBuildingResult

    init {
        val config = PagedList.Config.Builder()
            .setPageSize(PAGE_SIZE)
            .setEnablePlaceholders(true)
            .build()
        allAmenities = LivePagedListBuilder<Int, SocietyAmenity>(dataSourceFactory, config).build()
        networkState =
            Transformations.switchMap(dataSourceFactory.dataSourceLiveData) { it.getNetworkState() }
        refreshState =
            Transformations.switchMap(dataSourceFactory.dataSourceLiveData) { it.refreshState() }
    }

    fun events() {
        coroutineScope.launch {
            val result = dataSourceFactory.getRepo().loadEvents()
            withContext(Dispatchers.Main) {
                _eventResult.value = result
            }
        }
    }

    private fun clearAllEvents() = coroutineScope.launch {
        withContext(Dispatchers.IO) { dataSourceFactory.getRepo().clearEvents() }
    }

    fun refreshAllData() = dataSourceFactory.dataSourceLiveData.value?.refresh()

    fun retry() = dataSourceFactory.dataSourceLiveData.value?.retry()

    fun filterBySocietyBuildingId(id: String) =
        dataSourceFactory.dataSourceLiveData.value?.loadSocietyBuilding(id)

    fun getSocietyBuildingId(): String = dataSourceFactory.getRepo().getSocietyBuildingId()

    fun loadSocietyBuildings() {
        coroutineScope.launch {
            val result = dataSourceFactory.getRepo().getSocietyBuildingList()
            withContext(Dispatchers.Main) {
                _societyBuildingResult.value = result
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        dataSourceFactory.dataSourceLiveData.value?.onCleared()
        clearAllEvents()
        job.cancel()
    }
}