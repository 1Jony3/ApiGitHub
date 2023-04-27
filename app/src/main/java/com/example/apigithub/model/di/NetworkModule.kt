package com.example.apigithub.model.di

import com.example.apigithub.model.repository.AppRepository
import com.example.apigithub.model.repository.Repository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class NetworkModule {

    @Binds
    abstract fun bindAppRepository(
        appRepository: AppRepository
    ): Repository
}