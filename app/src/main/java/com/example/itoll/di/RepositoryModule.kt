package com.example.itoll.di

import com.example.itoll.data.api.UserApi
import com.example.itoll.data.rpository.UserRepositoryImp
import com.example.itoll.domain.repository.UserRepository
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
    fun provideRepository(api: UserApi): UserRepository {
        return UserRepositoryImp(api)
    }
}