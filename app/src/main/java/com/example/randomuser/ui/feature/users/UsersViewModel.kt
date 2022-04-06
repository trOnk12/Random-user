package com.example.randomuser.ui.feature.users

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.randomuser.domain.model.User
import com.example.randomuser.domain.usecase.GetPagingUsersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class UsersViewModel
@Inject constructor(
    getPagingUsersUseCase: GetPagingUsersUseCase
) : ViewModel() {

    val pagedUserList: Flow<PagingData<User>> =
        getPagingUsersUseCase(PagingConfig(pageSize = 20))

}
