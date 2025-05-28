package com.jeanmeza.dispensapp

import android.content.Context
import androidx.room.Room
import com.jeanmeza.dispensapp.data.local.DispensAppDatabase
import com.jeanmeza.dispensapp.data.local.dao.ItemDao
import com.jeanmeza.dispensapp.data.repository.ItemRepository
import com.jeanmeza.dispensapp.data.repository.LocalItemRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DispensAppModule {
    @Provides
    @Singleton
    fun provideDipensAppDatabase(@ApplicationContext context: Context): DispensAppDatabase {
        return Room.databaseBuilder(context, DispensAppDatabase::class.java, "dispensapp_database")
            .fallbackToDestructiveMigration(true)
            .build()
    }

    @Provides
    @Singleton
    fun provideItemDao(database: DispensAppDatabase): ItemDao {
        return database.itemDao()
    }

    @Provides
    @Singleton
    fun provideItemRepository(itemDao: ItemDao): ItemRepository {
        return LocalItemRepository(itemDao)

    }
}