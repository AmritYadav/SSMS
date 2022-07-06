package com.sts.ssms.data.society.repository

import com.sts.ssms.data.common.Result
import com.sts.ssms.data.database.entity.*
import com.sts.ssms.data.society.model.Flat
import com.sts.ssms.data.society.model.Society
import kotlinx.coroutines.runBlocking

class SocietyRepository(
    private val societyLocalDataSource: SocietyLocalDataSource,
    private val societyRemoteDataSource: SocietyRemoteDataSource
) {

    private val menuItems = ArrayList<Int>()

    private var _societyList: List<Society>? = null
    val societyList: List<Society>?
        get() = _societyList

    private var _flatList: List<Flat>? = null
    val flatList: List<Flat>?
        get() = _flatList

    private var _menuList: List<Int> = menuItems
    val menuList: List<Int>
        get() = _menuList

    fun getSocietyId() = societyLocalDataSource.societyId

    fun setSocietyId(societyId: String?) {
        societyLocalDataSource.societyId = societyId
    }

    fun saveSocieties(societyResult: SocietyResult, societyId: String? = null) {
        loadUserSociety(societyResult, societyId)
        societyLocalDataSource.saveSocieties(societyResult)
    }

    fun loadSocietyFromRemoteLocal(societyId: String? = null): SwitchSocietyResult = runBlocking {
        val userId = societyLocalDataSource.userId
        if (userId != null) {
            return@runBlocking when (val result =
                societyRemoteDataSource.societies(userId, societyId)) {
                is Result.Success -> {
                    saveSocieties(result.data, societyId)
                    SwitchSocietyResult(success = societyId?.toInt())
                }
                is Result.Error -> {
                    loadUserSociety(societyId = societyLocalDataSource.societyId)
                    SwitchSocietyResult(error = "Error switching Society")
                }
            }
        }
        return@runBlocking SwitchSocietyResult(error = "Error loading Societies")
    }

    private fun loadUserSociety(societyResult: SocietyResult? = null, societyId: String? = null) {
        setMenuFromLocal(societyResult?.menu)
        setUserSocietyFromLocal(societyResult?.societyList, societyId)
        societyLocalDataSource.societyId?.let { setUserFlatFromLocal(it, societyResult?.flatList) }
    }

    private fun setUserSocietyFromLocal(
        societyList: List<SocietyEntity>? = null, societyId: String? = null
    ) {
        if (societyList.isNullOrEmpty()) {
            val societies = societyLocalDataSource.getSociety()
            if (!societies.isNullOrEmpty()) {
                _societyList = societies.map { it.toSociety() }
                setSocietyId(societyId ?: societies[0].societyId)
            }
        } else {
            _societyList = societyList.map { it.toSociety() }
            setSocietyId(societyId ?: societyList[0].societyId)
        }
    }

    private fun setMenuFromLocal(menuEntity: MenuEntity? = null) {
        _menuList = menuEntity?.toMenu() ?: societyLocalDataSource.getMenuBySociety().toMenu()
    }

    fun transformSetMenuEntityToMenu(menuEntity: MenuEntity?) {
        _menuList = menuEntity.toMenu()
    }

    private fun setUserFlatFromLocal(societyId: String, flatList: List<FlatEntity>? = null) {
        if (flatList.isNullOrEmpty()) {
            val flats = societyLocalDataSource.getFlats(societyId)
            if (!flats.isNullOrEmpty()) {
                _flatList = flats.map { it.toFlat() }
            }
        } else {
            _flatList = flatList.map { it.toFlat() }
        }
    }

    fun clearSocieties() {
        _societyList = null
        _menuList = emptyList()
        societyLocalDataSource.societyId = null
        societyLocalDataSource.clearSocietyEntries()
    }
}

data class SocietyResult(
    var societyList: List<SocietyEntity>? = null,
    val flatList: List<FlatEntity>? = null,
    val menu: MenuEntity? = null
)

data class SwitchSocietyResult(
    val success: Int? = null,
    val error: String? = null
)