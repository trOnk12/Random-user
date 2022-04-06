package com.example.randomuser.ui.feature.users

import androidx.lifecycle.ViewModel
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.randomuser.data.source.local.entity.UserEntity
import com.example.randomuser.domain.usecase.GetPagingUsersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ExperimentalPagingApi
@HiltViewModel
class UsersViewModel
@Inject constructor(
    getPagingUsersUseCase: GetPagingUsersUseCase
) : ViewModel() {

    val pagedUserList: Flow<PagingData<UserEntity>> =
        getPagingUsersUseCase(PagingConfig(pageSize = 10))

}
