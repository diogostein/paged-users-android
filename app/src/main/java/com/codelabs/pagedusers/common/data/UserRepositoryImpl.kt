package com.codelabs.pagedusers.common.data

import com.codelabs.pagedusers.common.api.RandomUserMeApi
import com.codelabs.pagedusers.common.api.dto.ResultWrapperDto
import com.codelabs.pagedusers.common.api.dto.UserDto
import kotlinx.coroutines.delay

class UserRepositoryImpl(private val api: RandomUserMeApi) : UserRepository {

    override suspend fun getUsers(page: Int): ResultWrapperDto<UserDto> {
        // Delay the request for 2 seconds only to be able to see progress indicator when loading
        delay(2000)

        return api.getUsers(page = page)
    }

}