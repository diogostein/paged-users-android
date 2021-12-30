package com.codelabs.pagedusers.userlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.codelabs.pagedusers.common.api.dto.toUser
import com.codelabs.pagedusers.common.data.UserRepository
import com.codelabs.pagedusers.common.models.User
import com.codelabs.pagedusers.userlist.paging.UserPagingSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class UserListViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    fun getUsersFlow(): Flow<PagingData<User>> {
        return Pager(PagingConfig(pageSize = 20, prefetchDistance = 1)) {
            UserPagingSource(userRepository)
        }.flow
            .map { pagingData -> pagingData.map { it.toUser() } }
            .cachedIn(viewModelScope)
    }

}