package com.codelabs.pagedusers.common.di

import com.codelabs.pagedusers.common.api.RandomUserMeApi
import com.codelabs.pagedusers.common.data.UserRepository
import com.codelabs.pagedusers.common.data.UserRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object ViewModelModule {

    @Provides
    @ViewModelScoped
    fun provideUserRepository(api: RandomUserMeApi): UserRepository {
        return UserRepositoryImpl(api)
    }

}