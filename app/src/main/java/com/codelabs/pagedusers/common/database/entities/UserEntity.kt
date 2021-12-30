package com.codelabs.pagedusers.common.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.codelabs.pagedusers.common.models.User

@Entity(
    tableName = "users",
    indices = [Index(value = ["email"], unique = true)]
)
data class UserEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "email") val email: String,
    @ColumnInfo(name = "name") val name: String?,
    @ColumnInfo(name = "gender") val gender: String?,
    @ColumnInfo(name = "picture_url") val pictureUrl: String?
)

fun UserEntity.toUser(): User {
    return User(
        name = name ?: "",
        email = email,
        gender = gender ?: "",
        pictureUrl = pictureUrl,
    )
}
