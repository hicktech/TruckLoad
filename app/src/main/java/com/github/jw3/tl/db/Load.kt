package com.github.jw3.tl.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Load(
    @ColumnInfo(name = "driver") val driver: String,
    @ColumnInfo(name = "duration") val loadTime: Int,
    @ColumnInfo(name = "estimated") val bushelEstimate: Double,
    @ColumnInfo(name = "datetime_loaded") val loaded: Long,
    ) {
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null

    @ColumnInfo(name = "actual")
    var bushelActual: Int? = null


    @ColumnInfo(name = "datetime_delivered")
    var delivered: Long? = null
}
