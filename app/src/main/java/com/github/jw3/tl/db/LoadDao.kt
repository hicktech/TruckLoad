package com.github.jw3.tl.db

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface LoadDao {
    @Query("SELECT * FROM load ORDER BY id DESC")
    fun all(): List<Load>

    @Query("SELECT * FROM load ORDER BY id DESC")
    fun paged(): PagingSource<Int, Load>

    @Insert
    fun insert(vararg loads: Load)

    @Delete
    fun delete(load: Load)
}
