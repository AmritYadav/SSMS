package com.sts.ssms.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.sts.ssms.data.society.model.Flat

@Entity(tableName = "flat", indices = [Index(value = ["flat_id"], unique = true)])
data class FlatEntity(
    @PrimaryKey(autoGenerate = true) val id: Long,
    @ColumnInfo(name = "society_id") val societyId: String,
    @ColumnInfo(name = "building_name") val buildingName: String,
    @ColumnInfo(name = "flat_id") val flatId: String,
    @ColumnInfo(name = "flat_no") val flatNo: String,
    @ColumnInfo(name = "relation") val relation: String,
    @ColumnInfo(name = "block_id") val blockId: String,
    @ColumnInfo(name = "block") val block: String
)

fun FlatEntity.toFlat(): Flat {
    return Flat(
        flatId.toInt(),
        societyId.toInt(),
        flatNo,
        buildingName,
        block,
        "$flatNo-$block-$buildingName"
    )
}
