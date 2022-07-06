package com.sts.ssms.data.helpdesk.features.repository

class FeaturesRepository(
    private val localDataSource: FeaturesLocalDataSource
) {
    fun features() = localDataSource.featuresRawString()
}