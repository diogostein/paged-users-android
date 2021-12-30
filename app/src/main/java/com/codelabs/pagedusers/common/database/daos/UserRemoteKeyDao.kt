package com.codelabs.pagedusers.common.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.codelabs.pagedusers.common.database.entities.UserRemoteKeyEntity

@Dao
interface UserRemoteKeyDao {
    @Query("SELECT * FROM user_remote_keys WHERE email = :email")
    suspend fun findByEmail(email: String): UserRemoteKeyEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(remoteKeys: List<UserRemoteKeyEntity>)

    @Query("DELETE FROM user_remote_keys")
    fun clearAll()
}