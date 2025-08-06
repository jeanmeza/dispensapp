package com.jeanmeza.dispensapp

import android.content.Context
import android.util.Log
import androidx.room.Room
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.jeanmeza.dispensapp.data.local.DispensAppDatabase
import com.jeanmeza.dispensapp.data.local.dao.CategoryDao
import com.jeanmeza.dispensapp.data.local.dao.ItemDao
import com.jeanmeza.dispensapp.data.repository.CategoryRepository
import com.jeanmeza.dispensapp.data.repository.ItemRepository
import com.jeanmeza.dispensapp.data.repository.LocalCategoryRepository
import com.jeanmeza.dispensapp.data.repository.LocalItemRepository
import com.jeanmeza.dispensapp.network.BarcodeApiService
import com.jeanmeza.dispensapp.network.BarcodeRepository
import com.jeanmeza.dispensapp.network.NetworkBarcodeRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import java.util.concurrent.Executors
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DispensAppModule {
    private const val BASE_URL = "http://192.168.1.50:1880"

    @Provides
    @Singleton
    fun provideDipensAppDatabase(@ApplicationContext context: Context): DispensAppDatabase {
        val builder =
            Room.databaseBuilder(context, DispensAppDatabase::class.java, "dispensapp_database")
                .fallbackToDestructiveMigration(true)
        if (BuildConfig.DEBUG) {
            builder.setQueryCallback(
                { sqlQuery, bindArgs ->
                    Log.d("RoomQuery", "SQL Query: $sqlQuery SQL Args: $bindArgs")
                },
                Executors.newSingleThreadExecutor()
            )
        }
        return builder.build()
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

    @Provides
    @Singleton
    fun provideCategoryDao(database: DispensAppDatabase): CategoryDao {
        return database.categoryDao()
    }

    @Provides
    @Singleton
    fun provideCategoryRepository(categoryDao: CategoryDao): CategoryRepository {
        return LocalCategoryRepository(categoryDao)
    }

    @Provides
    @Singleton
    fun provideBarcodeApiService(): BarcodeApiService {
        val contentType = "application/json".toMediaType()
        val json = Json { ignoreUnknownKeys = true }

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(json.asConverterFactory(contentType))
            .build()
            .create(BarcodeApiService::class.java)
    }

}

@Module
@InstallIn(SingletonComponent::class)
abstract class DispensAppBindingModule {

    @Binds
    abstract fun bindBarcodeRepository(repository: NetworkBarcodeRepository): BarcodeRepository
}