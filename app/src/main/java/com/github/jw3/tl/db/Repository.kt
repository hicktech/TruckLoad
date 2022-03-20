package com.github.jw3.tl.db

import androidx.paging.PagingSource
import javax.inject.Inject

class Repository @Inject constructor(private val loads: LoadDao) {
    fun allLoads(): List<Load> {
        return loads.all()
    }

    fun pagedLoads(): PagingSource<Int, Load> {
        return loads.paged()
    }

    fun insertLoad(load: Load) {
        loads.insert(load)
    }
}
