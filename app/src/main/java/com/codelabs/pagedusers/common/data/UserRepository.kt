package com.codelabs.pagedusers.common.data

import com.codelabs.pagedusers.common.api.dto.ResultWrapperDto
import com.codelabs.pagedusers.common.api.dto.UserDto

interface UserRepository {
    suspend fun getUsers(page: Int): ResultWrapperDto<UserDto>
}