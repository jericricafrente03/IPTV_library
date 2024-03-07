package com.bittelasia.network.di

import com.bittelasia.network.data.local.MeshDataBase
import com.bittelasia.network.data.remote.IptvListAPI
import com.bittelasia.network.data.repository.MeshRepositoryImpl
import com.bittelasia.network.domain.repository.MeshRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideMeshListRepository(
        meshDB: MeshDataBase,
        apiService: IptvListAPI
    ): MeshRepository {
        return MeshRepositoryImpl(
            meshDataBase = meshDB,
            api = apiService
        )
    }
}