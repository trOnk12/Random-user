package com.example.randomuser.domain.usecase

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.randomuser.domain.model.User
import com.example.randomuser.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPagingUsersUseCase
@Inject constructor(
    private val userRepository: UserRepository
) {

    operator fun invoke(pagingConfig: PagingConfig): Flow<PagingData<User>> =
        Pager(
            config = pagingConfig,
            pagingSourceFactory = userRepository::userPagingSource
        ).flow

}

