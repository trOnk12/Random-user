package com.example.randomuser.domain.usecase

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.example.randomuser.data.source.local.database.AppDatabase
import com.example.randomuser.data.source.local.entity.UserEntity
import com.example.randomuser.data.source.remote.api.RandomUserApi
import com.example.randomuser.domain.mapper.UserResponseMapper
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@ExperimentalPagingApi
class UserMediator @Inject constructor(
    private val appDatabase: AppDatabase,
    private val randomUserApi: RandomUserApi,
    private val userResponseMapper: UserResponseMapper
) : RemoteMediator<Int, UserEntity>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, UserEntity>
    ): MediatorResult {
        return try {
            // The network load method takes an optional `after=<user.id>` parameter. For every
            // page after the first, we pass the last user ID to let it continue from where it
            // left off. For REFRESH, pass `null` to load the first page.
            val loadKey = when (loadType) {
                LoadType.REFRESH -> 1
                // In this example, we never need to prepend, since REFRESH will always load the
                // first page in the list. Immediately return, reporting end of pagination.
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {


                }
            }


            MediatorResult.Success(endOfPaginationReached = false)

        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }

}