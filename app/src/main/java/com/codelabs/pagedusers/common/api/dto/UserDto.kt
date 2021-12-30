package com.codelabs.pagedusers.common.api.dto

import com.codelabs.pagedusers.common.database.entities.UserEntity
import com.codelabs.pagedusers.common.models.User
import com.google.gson.annotations.SerializedName

data class UserDto(
    @SerializedName("email")
    val email: String,
    @SerializedName("gender")
    val gender: String?,
    @SerializedName("name")
    val name: NameDto?,
    @SerializedName("picture")
    val picture: PictureDto?
)

fun UserDto.toUser(): User {
    return User(
        name = if (name != null) "${name.first} ${name.last}" else "",
        email = email,
        gender = gender ?: "",
        pictureUrl = picture?.medium
    )
}

fun UserDto.toUserEntity(): UserEntity {
    return UserEntity(
        name = "${name?.first} ${name?.last}",
        email = email,
        gender = gender,
        pictureUrl = picture?.medium
    )
}