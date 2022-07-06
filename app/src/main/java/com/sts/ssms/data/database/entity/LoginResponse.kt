package com.sts.ssms.data.database.entity

data class LoginResponse(
    val userEntity: UserEntity? = null,
    val societyEntityList: List<SocietyEntity>? = null,
    var menuEntity: MenuEntity? = null,
    var flatEntityList: List<FlatEntity>? = null,
    val passwordChanged: Int = -1
)