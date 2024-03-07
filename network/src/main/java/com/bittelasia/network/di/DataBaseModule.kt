package com.bittelasia.network.di

import android.content.Context
import androidx.room.Room
import com.bittelasia.network.data.local.MeshDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataBaseModule {
    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): MeshDataBase {
        return Room.databaseBuilder(
            context,
            MeshDataBase::class.java,
            "mesh_db"
        ).build()
    }
}