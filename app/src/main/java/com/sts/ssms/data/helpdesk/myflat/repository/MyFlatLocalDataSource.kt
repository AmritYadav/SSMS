package com.sts.ssms.data.helpdesk.myflat.repository

import com.sts.ssms.data.society.model.Flat
import com.sts.ssms.data.society.repository.SocietyRepository

class MyFlatLocalDataSource(private val societyRepository: SocietyRepository) {

    fun getUserFlats(): List<Flat>? {
        return societyRepository.flatList
    }

}