package com.example.randomuser.data.source.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.randomuser.data.source.remote.UserRemoteSource
import com.example.randomuser.domain.model.User
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class UsersPagingSource @Inject constructor(
    private val userRemoteSource: UserRemoteSource
) {
    companion object {
        const val DEFAULT_PAGE_INDEX = 1
    }

    fun getPagingSource(): PagingSource<Int, User> {
        return object : PagingSource<Int, User>() {

            override fun getRefreshKey(state: PagingState<Int, User>): Int? {
                return state.anchorPosition?.let { anchorPosition ->
                    val anchorPage = state.closestPageToPosition(anchorPosition)
                    anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
                }
            }

            override suspend fun load(params: LoadParams<Int>): LoadResult<Int, User> {
                val page = params.key ?: DEFAULT_PAGE_INDEX

                return try {
                    val response = userRemoteSource.getUsers(page)
                    LoadResult.Page(
                        response, prevKey = if (page == 1) null else page - 1,
                        nextKey = if (response.isEmpty()) null else page + 1
                    )
                } catch (exception: IOException) {
                    return LoadResult.Error(exception)
                } catch (exception: HttpException) {
                    return LoadResult.Error(exception)
                }
            }
        }
    }

}