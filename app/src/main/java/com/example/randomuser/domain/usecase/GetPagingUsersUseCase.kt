package com.example.randomuser.domain.usecase

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.randomuser.data.source.local.entity.UserEntity
import com.example.randomuser.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ExperimentalPagingApi
class GetPagingUsersUseCase
@Inject constructor(
    private val userRepository: UserRepository,
    private val userMediator: UserMediator
) {

    operator fun invoke(pagingConfig: PagingConfig): Flow<PagingData<UserEntity>> =
        Pager(
            config = pagingConfig,
            pagingSourceFactory = userRepository::userPagingSource,
            remoteMediator = userMediator
        ).flow

}

