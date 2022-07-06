package com.sts.ssms.data.society.model

data class Flat(
    val flatId: Int,
    val societyId: Int,
    val flatNo: String,
    val building: String,
    val block: String,
    val display: String // FlatNo + Block + Building
) {
    override fun toString(): String {
        return display
    }
}