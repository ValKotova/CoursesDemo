package com.magni.coursesdemo.di

import android.content.Context
import androidx.room.Room
import com.magni.coursesdemo.data.database.AppDatabase
import com.magni.coursesdemo.data.database.CourceDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "app-database"
        ).build()
    }

    @Provides
    fun provideCourceDao(database: AppDatabase): CourceDao {
        return database.courceDao()
    }
}