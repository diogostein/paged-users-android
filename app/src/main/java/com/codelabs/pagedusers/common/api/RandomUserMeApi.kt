package com.codelabs.pagedusers.common.api

import com.codelabs.pagedusers.common.api.dto.ResultWrapperDto
import com.codelabs.pagedusers.common.api.dto.UserDto
import retrofit2.http.GET
import retrofit2.http.Query

interface RandomUserMeApi {
    @GET("1.3/")
    suspend fun getUsers(
        @Query("page") page: Int = 1,
        @Query("results") results: Int = 20,
        @Query("nat") nat: String = "us",
        @Query("seed") seed: String = "user",
        @Query("exc") exc: String = "login,location,dob,registered,phone,cell,id,nat"
    ): ResultWrapperDto<UserDto>
}