package com.sts.ssms.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.sts.ssms.custom.calendar.models.Event

@Entity(tableName = "event", indices = [Index(value = ["event_id"], unique = true)])
data class EventEntity(
    @PrimaryKey(autoGenerate = true) val id: Long,
    @ColumnInfo(name = "event_id") val eventId: Int,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "start_ts") val startTs: Long,
    @ColumnInfo(name = "end_ts") val endTs: Long
)

fun EventEntity.toEvent(): Event = Event(
    id = eventId,
    title = name,
    startTS = startTs,
    endTS = endTs
)