package com.example.itoll.di

import com.example.itoll.data.api.Api
import com.example.itoll.data.rpository.RepositoryImp
import com.example.itoll.domain.repository.Repository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    @Singleton
    fun provideRepository(api: Api): Repository {
        return RepositoryImp(api)
    }
}