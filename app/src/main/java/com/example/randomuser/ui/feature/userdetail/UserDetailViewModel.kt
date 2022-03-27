package com.example.randomuser.ui.feature.userdetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.randomuser.ui.feature.userdetail.model.UserDetail
import com.example.randomuser.ui.feature.users.GetUsersLocalCacheProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    getUsersLocalCacheProvider: GetUsersLocalCacheProvider
) : ViewModel() {

    var userDetailState = MutableStateFlow(UserDetailState())

    init {
        val user = getUsersLocalCacheProvider.getUser(savedStateHandle.get<String>("userId")!!)

        viewModelScope.launch {
            userDetailState.emit(
                UserDetailState(
                    userDetail = UserDetail(
                        name = user.name,
                        gender = "female"
                    )
                )
            )
        }
    }

}

data class UserDetailState(val userDetail: UserDetail? = null)