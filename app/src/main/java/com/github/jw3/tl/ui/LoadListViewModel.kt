package com.github.jw3.tl.ui

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.github.jw3.tl.db.Load
import com.github.jw3.tl.db.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class LoadListViewModel @Inject constructor(
    private val db: Repository,
) : ViewModel(), LifecycleObserver {

    fun getPaged(): Flow<PagingData<Load>> {
        return Pager(config = PagingConfig(pageSize = 20, maxSize = 200),
            pagingSourceFactory = {db.pagedLoads()}).flow.cachedIn(viewModelScope)
    }
}
