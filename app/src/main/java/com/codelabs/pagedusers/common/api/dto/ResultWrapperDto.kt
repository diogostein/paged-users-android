package com.codelabs.pagedusers.common.api.dto

import com.google.gson.annotations.SerializedName

data class ResultWrapperDto<T>(
    @SerializedName("results")
    val results: List<T>
)
