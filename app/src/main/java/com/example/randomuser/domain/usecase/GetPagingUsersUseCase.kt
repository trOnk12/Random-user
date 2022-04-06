package com.example.randomuser.domain.usecase

import androidx.paging.*
import com.example.randomuser.data.mapper.UserEntityMapper
import com.example.randomuser.domain.model.User
import com.example.randomuser.domain.remotemediator.UserRemoteMediator
import com.example.randomuser.domain.repository.UserPagingRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@ExperimentalPagingApi
class GetPagingUsersUseCase
@Inject constructor(
    private val userPagingRepository: UserPagingRepository,
    private val userEntityMapper: UserEntityMapper,
    private val userRemoteMediator: UserRemoteMediator
) {

    operator fun invoke(pagingConfig: PagingConfig): Flow<PagingData<User>> =
        Pager(
            config = pagingConfig,
            pagingSourceFactory = userPagingRepository::userPagingSource,
            remoteMediator = userRemoteMediator
        ).flow.map { pagingData ->
            pagingData.map { userEntity ->
                userEntityMapper.toUser(
                    userEntity
                )
            }
        }

}

