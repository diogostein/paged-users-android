package com.codelabs.pagedusers.common.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "user_remote_keys",
    indices = [Index(value = ["email"], unique = true)]
)
data class UserRemoteKeyEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "email") val email: String,
    @ColumnInfo(name = "prev_key") val prevKey: Int?,
    @ColumnInfo(name = "next_key") val nextKey: Int?
)