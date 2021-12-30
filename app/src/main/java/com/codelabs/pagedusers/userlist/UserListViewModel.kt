package com.codelabs.pagedusers.userlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.codelabs.pagedusers.common.database.AppDatabase
import com.codelabs.pagedusers.common.database.entities.toUser
import com.codelabs.pagedusers.common.models.User
import com.codelabs.pagedusers.userlist.paging.UserRemoteMediator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class UserListViewModel @Inject constructor(
    private val userRemoteMediator: UserRemoteMediator,
    private val database: AppDatabase
) : ViewModel() {

    @OptIn(ExperimentalPagingApi::class)
    fun getUsersFlow(): Flow<PagingData<User>> {
        return Pager(
            config = PagingConfig(pageSize = 20, prefetchDistance = 1),
            remoteMediator = userRemoteMediator
        ) {
            database.userDao().pagingSource()
        }.flow
            .map { pagingData -> pagingData.map { it.toUser() } }
            .cachedIn(viewModelScope)
    }

}