package com.example.randomuser.ui.feature.userdetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.randomuser.domain.usecase.GetUserUseCase
import com.example.randomuser.ui.feature.userdetail.model.UserDetail
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    getUserUseCase: GetUserUseCase,
) : ViewModel() {

    var userDetailState = MutableStateFlow(UserDetailState())

    init {
        viewModelScope.launch {
            userDetailState.emit(
                UserDetailState(
                    isLoading = true
                )
            )

            val user = getUserUseCase((savedStateHandle.get<String>("userId")!!))

            userDetailState.emit(
                UserDetailState(
                    isLoading = false,
                    userDetail = UserDetail(
                        name = user.name,
                        gender = "female"
                    )
                )
            )
        }
    }

}

data class UserDetailState(val userDetail: UserDetail? = null, val isLoading: Boolean = false)