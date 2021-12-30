package com.codelabs.pagedusers.userlist.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.codelabs.pagedusers.common.api.dto.UserDto
import com.codelabs.pagedusers.common.data.UserRepository
import retrofit2.HttpException
import java.io.IOException

class UserPagingSource(
    private val userRepository: UserRepository
) : PagingSource<Int, UserDto>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UserDto> {
        return try {
            val page = params.key ?: 1
            val response = userRepository.getUsers(page)

            LoadResult.Page(
                data = response.results,
                prevKey = null,
                nextKey = if (response.results.isEmpty()) null else page + 1,
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, UserDto>): Int? {
        // Try to find the page key of the closest page to anchorPosition, from
        // either the prevKey or the nextKey, but you need to handle nullability
        // here:
        //  * prevKey == null -> anchorPage is the first page.
        //  * nextKey == null -> anchorPage is the last page.
        //  * both prevKey and nextKey null -> anchorPage is the initial page, so
        //    just return null.
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

}