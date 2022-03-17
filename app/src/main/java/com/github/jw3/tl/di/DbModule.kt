package com.github.jw3.tl.di

import android.app.Application
import com.github.jw3.tl.db.LoadDao
import com.github.jw3.tl.db.LoadsDB
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class DbModule {

    @Singleton
    @Provides
    fun getAppDatabase(context: Application): LoadsDB {
        return LoadsDB.getAppDbInstance(context)
    }

    @Singleton
    @Provides
    fun loadDao(db: LoadsDB): LoadDao {
        return db.loadsDao()
    }
}
