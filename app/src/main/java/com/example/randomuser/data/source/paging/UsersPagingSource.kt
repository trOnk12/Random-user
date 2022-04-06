package com.example.randomuser.data.source.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.randomuser.data.source.local.UserLocalSource
import com.example.randomuser.data.source.remote.UserRemoteSource
import com.example.randomuser.domain.model.User
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class UsersPagingSource @Inject constructor(
    private val userRemoteSource: UserRemoteSource,
    private val userLocalSource: UserLocalSource
) {
    companion object {
        const val DEFAULT_PAGE_INDEX = 1
    }

    fun getPagingSource(): PagingSource<Int, User> {
        return object : PagingSource<Int, User>() {
            private var previousResult = emptyList<User>()

            override fun getRefreshKey(state: PagingState<Int, User>): Int? {
                return state.anchorPosition?.let { anchorPosition ->
                    val anchorPage = state.closestPageToPosition(anchorPosition)
                    anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
                }
            }

            override suspend fun load(params: LoadParams<Int>): LoadResult<Int, User> {
                val nextPageNumber = params.key ?: DEFAULT_PAGE_INDEX

                return try {
                    // ideally we would use here for example a RemoteMediator that would fetch from remote

                    userLocalSource.addUsers(userRemoteSource.getUsers(nextPageNumber-1))

                    previousResult = userRemoteSource.getUsers(nextPageNumber)

                    LoadResult.Page(
                        previousResult,
                        prevKey = if (nextPageNumber == 1) null else nextPageNumber - 1,
                        nextKey = if (previousResult.isEmpty()) null else nextPageNumber + 1
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