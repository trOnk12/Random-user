package com.example.randomuser.domain.remotemediator

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.randomuser.data.mapper.UserResponseMapper
import com.example.randomuser.data.source.local.database.AppDatabase
import com.example.randomuser.data.source.local.entity.UserEntity
import com.example.randomuser.data.source.local.entity.UserRemoteKeyEntity
import com.example.randomuser.data.source.remote.api.RandomUserApi
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@ExperimentalPagingApi
class UserRemoteMediator @Inject constructor(
    private val appDatabase: AppDatabase,
    private val randomUserApi: RandomUserApi,
    private val userResponseMapper: UserResponseMapper
) : RemoteMediator<Int, UserEntity>() {
    companion object {
        private const val DEFAULT_PAGE_INDEX = 0
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, UserEntity>
    ): MediatorResult {
        return try {
            val loadKey = when (loadType) {
                LoadType.REFRESH -> {
                    val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                    remoteKeys?.nextPage?.minus(1) ?: DEFAULT_PAGE_INDEX
                }
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    val remoteKeys = getRemoteKeyForLastItem(state)
                        ?: return MediatorResult.Success(endOfPaginationReached = false)
                    remoteKeys.nextPage
                        ?: return MediatorResult.Success(endOfPaginationReached = true)
                }
            }

            val response = randomUserApi.getUsers(
                loadKey,
                state.config.pageSize,
                "abc"
            )

            appDatabase.withTransaction {
                val prevPage = if (loadKey == DEFAULT_PAGE_INDEX) null else loadKey - 1
                val nextPage = loadKey + 1

                val keys = response.results.map { user ->
                    UserRemoteKeyEntity(userId = user.login.uuid, prevPage, nextPage)
                }

                appDatabase.userRemoteKeyDao().insertAll(keys)
                appDatabase.userDao().insertAll(
                    users = userResponseMapper.toEntities(response)
                )
            }

            //since the API is spawning endless result we always return false
            MediatorResult.Success(endOfPaginationReached = false)

        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, UserEntity>): UserRemoteKeyEntity? {
        return state.lastItemOrNull()?.let { user ->
            appDatabase.withTransaction { appDatabase.userRemoteKeyDao().getRemoteKey(user.id) }
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, UserEntity>): UserRemoteKeyEntity? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                appDatabase.withTransaction { appDatabase.userRemoteKeyDao().getRemoteKey(id) }
            }
        }
    }

}