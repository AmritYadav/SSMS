package com.sts.ssms.ui.helpdesk.features

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sts.ssms.data.helpdesk.features.repository.FeaturesRepository
import kotlinx.coroutines.*

class FeaturesViewModel(private val featuresRepository: FeaturesRepository) : ViewModel() {

    private val job = Job()
    private val coroutineScope = CoroutineScope(Dispatchers.IO + job)

    private var _features = MutableLiveData<String>()
    val features: LiveData<String> = _features

    fun ssmsFeatures() {
        _features.value = featuresRepository.features()
    }
}