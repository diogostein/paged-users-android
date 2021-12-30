package com.codelabs.pagedusers.common.database.daos

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.codelabs.pagedusers.common.database.entities.UserEntity

@Dao
interface UserDao {
    @Query("SELECT * FROM users")
    fun pagingSource(): PagingSource<Int, UserEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(users: List<UserEntity>)

    @Query("DELETE FROM users")
    fun clearAll()
}