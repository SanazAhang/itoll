package com.example.itoll.di

import com.example.itoll.domain.repository.UserRepository
import com.example.itoll.domain.usecase.GetUsersUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {

    @Provides
    @Singleton
    fun getUseCase(userRepository: UserRepository): GetUsersUseCase =
        GetUsersUseCase(userRepository)
}