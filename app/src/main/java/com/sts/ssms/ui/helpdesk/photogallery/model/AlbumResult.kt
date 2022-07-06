package com.sts.ssms.ui.helpdesk.photogallery.model

data class AlbumResult(
    val albumList: List<AlbumUiModel>? = null,
    val error: String? = null
)