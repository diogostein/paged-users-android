package com.codelabs.pagedusers.common.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.codelabs.pagedusers.common.database.daos.UserDao
import com.codelabs.pagedusers.common.database.daos.UserRemoteKeyDao
import com.codelabs.pagedusers.common.database.entities.UserEntity
import com.codelabs.pagedusers.common.database.entities.UserRemoteKeyEntity

@Database(entities = [UserEntity::class, UserRemoteKeyEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun userRemoteKeyDao(): UserRemoteKeyDao
}