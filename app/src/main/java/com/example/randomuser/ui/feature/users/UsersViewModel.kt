package com.example.randomuser.ui.feature.users

import androidx.lifecycle.ViewModel
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.example.randomuser.domain.usecase.GetPagingUsersUseCase
import com.example.randomuser.ui.feature.users.model.UserItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@ExperimentalPagingApi
@HiltViewModel
class UsersViewModel
@Inject constructor(
    getPagingUsersUseCase: GetPagingUsersUseCase,
) : ViewModel() {

    val pagedUserList: Flow<PagingData<UserItem>> =
        getPagingUsersUseCase(PagingConfig(pageSize = 10)).map { user ->
            user.map {
                UserItem(
                    id = it.uuid,
                    name = it.name,
                    avatarUrl = it.avatarUrl
                )
            }
        }

}
