package com.codelabs.pagedusers.userlist.paging

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.codelabs.pagedusers.common.api.RandomUserMeApi
import com.codelabs.pagedusers.common.api.dto.toUserEntity
import com.codelabs.pagedusers.common.database.AppDatabase
import com.codelabs.pagedusers.common.database.entities.UserEntity
import com.codelabs.pagedusers.common.database.entities.UserRemoteKeyEntity
import com.codelabs.pagedusers.common.helpers.NetworkHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException
import java.io.InvalidObjectException

@OptIn(ExperimentalPagingApi::class)
class UserRemoteMediator(
    private val database: AppDatabase,
    private val networkApi: RandomUserMeApi,
    private val networkHelper: NetworkHelper
) : RemoteMediator<Int, UserEntity>() {

    companion object {
        const val TAG = "UserRemoteMediator"
    }

    override suspend fun load(loadType: LoadType, state: PagingState<Int, UserEntity>): MediatorResult {
        return try {
            val page = when (loadType) {
                LoadType.REFRESH -> {
                    val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                    remoteKeys?.nextKey?.minus(1) ?: 1
                }
                LoadType.PREPEND ->
                    return MediatorResult.Success(true)
                LoadType.APPEND -> {
                    val remoteKeys = getRemoteKeyForLastItem(state)
                        ?: throw InvalidObjectException("Result is empty")
                    remoteKeys.nextKey ?: return MediatorResult.Success(true)
                }
            }

            Log.d(TAG, "loadType: $loadType, page: $page")

            // TODO: Move code below to repository as improvement and best separation of concern

            // If there's no internet connection, load data only from database
            if (!networkHelper.isConnectionAvailable()) {
                return MediatorResult.Success(false)
            }

            // Here we could add more short-circuit rules to optimize caching before
            // fetching data from remote service...

            // Delay the request for 2 seconds only to be able to see progress indicator when loading
            delay(2000)

            val response = networkApi.getUsers(page = page)

            withContext(Dispatchers.IO) {
                database.runInTransaction {
                    if (loadType == LoadType.REFRESH) {
                        database.run {
                            userDao().clearAll()
                            userRemoteKeyDao().clearAll()
                        }
                    }

                    val remoteKeys = response.results.map {
                        UserRemoteKeyEntity(
                            email = it.email,
                            prevKey = if (page == 1) null else page - 1,
                            nextKey = if (response.results.isEmpty()) null else page + 1
                        )
                    }

                    val users = response.results.map { it.toUserEntity() }

                    database.run {
                        userRemoteKeyDao().insertAll(remoteKeys)
                        userDao().insertAll(users)
                    }
                }
            }

            MediatorResult.Success(endOfPaginationReached = response.results.isEmpty())
        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, UserEntity>): UserRemoteKeyEntity? {
        return state.lastItemOrNull()?.let { user ->
            database.userRemoteKeyDao().findByEmail(user.email)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, UserEntity>): UserRemoteKeyEntity? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.let { user ->
                database.userRemoteKeyDao().findByEmail(user.email)
            }
        }
    }

}