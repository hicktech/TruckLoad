package com.github.jw3.tl.db

import javax.inject.Inject

class Repository @Inject constructor(private val loads: LoadDao) {
    fun allLoads(): List<Load> {
        return loads.all()
    }

    fun insertLoad(load: Load) {
        loads.insert(load)
    }
}
