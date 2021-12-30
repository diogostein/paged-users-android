package com.codelabs.pagedusers.common.di

import com.codelabs.pagedusers.common.api.RandomUserMeApi
import com.codelabs.pagedusers.common.data.UserRepository
import com.codelabs.pagedusers.common.data.UserRepositoryImpl
import com.codelabs.pagedusers.common.database.AppDatabase
import com.codelabs.pagedusers.common.helpers.NetworkHelper
import com.codelabs.pagedusers.userlist.paging.UserRemoteMediator
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

    @Provides
    @ViewModelScoped
    fun provideUserRemoteMediator(
        database: AppDatabase,
        api: RandomUserMeApi,
        networkHelper: NetworkHelper
    ): UserRemoteMediator {
        return UserRemoteMediator(database, api, networkHelper)
    }

}